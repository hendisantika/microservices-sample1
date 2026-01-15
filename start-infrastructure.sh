#!/bin/bash

# ==========================================
# Infrastructure Startup Script
# ==========================================
# This script starts all required infrastructure
# services and waits for them to be healthy
# ==========================================

set -e

echo "🚀 Starting Microservices Infrastructure..."
echo ""

# Check if docker-compose is installed
if ! command -v docker-compose &> /dev/null; then
    echo "❌ docker-compose is not installed"
    echo "Please install Docker Desktop or docker-compose"
    exit 1
fi

# Check if Docker is running
if ! docker info &> /dev/null; then
    echo "❌ Docker is not running"
    echo "Please start Docker Desktop"
    exit 1
fi

# Start infrastructure services
echo "📦 Starting infrastructure services..."
docker-compose up -d

echo ""
echo "⏳ Waiting for services to be healthy..."
echo ""

# Wait for services to be healthy
MAX_ATTEMPTS=30
ATTEMPT=0

while [ $ATTEMPT -lt $MAX_ATTEMPTS ]; do
    HEALTHY_COUNT=$(docker-compose ps --format json | jq -r '.Health' | grep -c "healthy" || echo "0")

    if [ "$HEALTHY_COUNT" -ge 4 ]; then
        echo ""
        echo "✅ All infrastructure services are healthy!"
        echo ""
        break
    fi

    ATTEMPT=$((ATTEMPT + 1))
    echo -n "."
    sleep 2
done

if [ $ATTEMPT -eq $MAX_ATTEMPTS ]; then
    echo ""
    echo "⚠️  Warning: Some services may not be healthy yet"
    echo "Run 'docker-compose ps' to check status"
    echo ""
fi

# Display service status
echo "📊 Infrastructure Status:"
echo ""
docker-compose ps

echo ""
echo "🌐 Management UIs:"
echo "   - Kafka UI:        http://localhost:8090"
echo "   - Redis Commander: http://localhost:8081"
echo ""
echo "🔌 Service Endpoints:"
echo "   - Kafka:     localhost:9092"
echo "   - MySQL:     localhost:3306 (user: root, pass: root)"
echo "   - Redis:     localhost:6379"
echo "   - Zookeeper: localhost:2181"
echo ""
echo "💡 Useful Commands:"
echo "   - View logs:        docker-compose logs -f"
echo "   - Stop services:    docker-compose down"
echo "   - Restart services: docker-compose restart"
echo ""
echo "📖 For more details, see INFRASTRUCTURE.md"
echo ""
