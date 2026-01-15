# Infrastructure Setup Guide

This document describes the infrastructure components required for the microservices architecture and how to set them up using Docker Compose.

## Components

### Core Infrastructure

| Component | Version | Port | Purpose |
|-----------|---------|------|---------|
| **Zookeeper** | 7.6.0 | 2181 | Kafka coordination |
| **Kafka** | 7.6.0 | 9092 | Event streaming platform |
| **MySQL** | 8.3 | 3306 | Primary database |
| **Redis** | 7.2 | 6379 | Cache & session store |

### Management UIs

| Component | Port | Purpose |
|-----------|------|---------|
| **Kafka UI** | 8090 | Kafka topic & consumer group management |
| **Redis Commander** | 8081 | Redis key/value inspection |

## Quick Start

### 1. Start All Infrastructure

```bash
# Start all services in detached mode
docker-compose up -d

# View logs
docker-compose logs -f

# View logs for specific service
docker-compose logs -f kafka
```

### 2. Verify Health

```bash
# Check all services are running
docker-compose ps

# Expected output: All services should show "Up" and "healthy"
```

### 3. Stop Infrastructure

```bash
# Stop all services
docker-compose down

# Stop and remove volumes (⚠️ destroys data)
docker-compose down -v
```

## Individual Service Commands

### Kafka

```bash
# Start only Kafka and dependencies
docker-compose up -d zookeeper kafka

# Create a topic manually
docker-compose exec kafka kafka-topics \
  --create \
  --topic test-topic \
  --bootstrap-server localhost:9092 \
  --partitions 3 \
  --replication-factor 1

# List all topics
docker-compose exec kafka kafka-topics \
  --list \
  --bootstrap-server localhost:9092

# Describe a topic
docker-compose exec kafka kafka-topics \
  --describe \
  --topic order.created \
  --bootstrap-server localhost:9092

# View consumer groups
docker-compose exec kafka kafka-consumer-groups \
  --list \
  --bootstrap-server localhost:9092
```

### MySQL

```bash
# Connect to MySQL
docker-compose exec mysql mysql -uroot -proot

# Connect to microservices database
docker-compose exec mysql mysql -uroot -proot microservices

# Run a SQL script
docker-compose exec -T mysql mysql -uroot -proot microservices < your-script.sql

# Create a database backup
docker-compose exec mysql mysqldump -uroot -proot microservices > backup.sql

# Restore from backup
docker-compose exec -T mysql mysql -uroot -proot microservices < backup.sql
```

### Redis

```bash
# Connect to Redis CLI
docker-compose exec redis redis-cli

# Check Redis info
docker-compose exec redis redis-cli INFO

# Monitor Redis commands in real-time
docker-compose exec redis redis-cli MONITOR

# Get all keys
docker-compose exec redis redis-cli KEYS "*"

# Flush all data (⚠️ destructive)
docker-compose exec redis redis-cli FLUSHALL
```

## Accessing Management UIs

### Kafka UI
- URL: http://localhost:8090
- Features:
  - Browse topics and messages
  - View consumer groups and lag
  - Produce test messages
  - Monitor cluster health

### Redis Commander
- URL: http://localhost:8081
- Features:
  - Browse Redis keys
  - View/edit key values
  - Monitor Redis metrics
  - Execute Redis commands

## Data Persistence

Data is persisted in Docker volumes:

```bash
# List volumes
docker volume ls | grep microservices-sample1

# Inspect a volume
docker volume inspect microservices-sample1_mysql-data
docker volume inspect microservices-sample1_redis-data

# Remove volumes (⚠️ destroys all data)
docker-compose down -v
```

## Troubleshooting

### Services Won't Start

```bash
# Check logs for errors
docker-compose logs kafka
docker-compose logs mysql

# Restart a specific service
docker-compose restart kafka

# Rebuild and restart
docker-compose up -d --build
```

### Port Conflicts

If you have port conflicts, you can modify ports in `docker-compose.yml` or use a `.env` file:

```bash
# Create .env file from example
cp .env.example .env

# Edit .env and customize ports
# Then restart services
docker-compose down
docker-compose up -d
```

### Kafka Connection Issues

```bash
# Verify Kafka is healthy
docker-compose exec kafka kafka-broker-api-versions \
  --bootstrap-server localhost:9092

# Check Zookeeper connection
docker-compose exec kafka kafka-configs \
  --zookeeper zookeeper:2181 \
  --describe \
  --entity-type brokers \
  --entity-name 1
```

### MySQL Connection Issues

```bash
# Check MySQL is accepting connections
docker-compose exec mysql mysqladmin ping -h localhost -uroot -proot

# Verify user permissions
docker-compose exec mysql mysql -uroot -proot -e \
  "SELECT User, Host FROM mysql.user WHERE User='microservice_user';"
```

### Redis Connection Issues

```bash
# Ping Redis
docker-compose exec redis redis-cli PING

# Check Redis configuration
docker-compose exec redis redis-cli CONFIG GET "*"
```

## Resource Limits

The docker-compose setup uses default resource limits. For production-like testing, you can add resource constraints:

```yaml
services:
  kafka:
    # ... existing config
    deploy:
      resources:
        limits:
          cpus: '2'
          memory: 2G
        reservations:
          memory: 1G
```

## Network Configuration

All services are on the `microservices-network` bridge network. Services can communicate using their service names:

- `kafka:29092` (internal)
- `mysql:3306`
- `redis:6379`
- `zookeeper:2181`

From host machine:
- `localhost:9092` (Kafka)
- `localhost:3306` (MySQL)
- `localhost:6379` (Redis)

## Health Checks

All services include health checks:

```bash
# View health status
docker-compose ps

# Check specific service health
docker inspect --format='{{.State.Health.Status}}' kafka
```

## Clean Restart

To completely reset the infrastructure:

```bash
# Stop everything
docker-compose down -v

# Remove any orphaned containers
docker-compose down --remove-orphans

# Start fresh
docker-compose up -d

# Wait for health checks
sleep 30
docker-compose ps
```

## Production Considerations

For production deployments, consider:

1. **External Volumes**: Use external volumes or cloud storage
2. **Authentication**: Enable Kafka SASL/SSL, MySQL authentication
3. **Resource Limits**: Set appropriate CPU/memory limits
4. **Monitoring**: Add Prometheus, Grafana
5. **Backup Strategy**: Automated backups for MySQL
6. **HA Setup**: Multiple Kafka brokers, MySQL replication
7. **Network Isolation**: Separate networks for services
8. **Secrets Management**: Use Docker secrets or external secret stores

## Additional Resources

- [Kafka Documentation](https://kafka.apache.org/documentation/)
- [MySQL Docker Hub](https://hub.docker.com/_/mysql)
- [Redis Documentation](https://redis.io/documentation)
- [Docker Compose Documentation](https://docs.docker.com/compose/)
