# Production-Grade Microservices Patterns with Spring Boot 4

![Java CI with Gradle](https://github.com/hendisantika/microservices-sample1/workflows/Java%20CI%20with%20Gradle/badge.svg)

Event-driven, production-focused microservices architecture using Java 25 and Spring Boot 4. Focus areas: failure
handling, idempotency, retry, dead-letter topics (DLT) and safe recovery. This repository validates proven
distributed-system patterns on the latest JVM and Spring ecosystem.

## Table of Contents

- [Key Architecture Principles](#key-architecture-principles)
- [Services Overview](#services-overview)
- [Event-Driven Saga Choreography](#event-driven-saga-choreography)
- [Kafka, Retry & DLT Strategy](#kafka-retry--dlt-strategy)
- [Security Model](#security-model)
- [Idempotency Strategy](#idempotency-strategy)
- [Technology Stack](#technology-stack)
- [Important Note on Java 25 & Spring Boot 4](#important-note-on-java-25--spring-boot-4)
- [What This Project Proves](#what-this-project-proves)
- [Prerequisites](#prerequisites)
- [Building the Project](#building-the-project)
- [Running Tests](#running-tests)
- [How to Run](#how-to-run)
- [CI/CD](#cicd)

---

## Key Architecture Principles

- Gateway-first design
- Event-driven communication (Kafka)
- Saga choreography (no orchestration)
- No synchronous service-to-service calls
- Idempotency at every layer
- Kafka-native retry & DLT
- Manual, audited DLT replay
- Recovery-first mindset

---

## Services Overview

### 1. API Gateway (Reactive)

**Technology**

- Spring Cloud Gateway
- Spring WebFlux
- Spring Security

**Responsibilities**

- Single entry point
- JWT validation via JWKS
- Rate limiting, role-based routing, request logging

**Notes**

- Reactive by design (high concurrency)
- No DB access, no Kafka interaction

### 2. Auth Service (JWT RS256 + JWKS)

**Responsibilities**

- Issue JWT tokens
- Maintain private signing keys
- Expose public keys via JWKS

**Why RS256**

- No shared secrets, independent token validation, safe key rotation

### 3. Order Service (Saga Initiator)

**Responsibilities**

- Order lifecycle ownership
- HTTP idempotency (Idempotency-Key)
- Outbox pattern, saga timeout compensation

**Patterns**

- Outbox (DB + event atomicity), Kafka publishing, typed domain events

### 4. Payment Service (Critical Path)

**Responsibilities**

- Process payments, maintain immutable ledger
- Emit payment events, handle retries and DLT

**Key Guarantees**

- Append-only ledger, multi-layer idempotency, separation of business vs technical failures

### 5. Notification Service (Side-Effect Isolation)

**Responsibilities**

- Consume payment events, send notifications, track delivery attempts

**Design Goal**

- Failures here must never block business flow

### 6. DLT Replay Admin Service

**Responsibilities**

- ADMIN-only access
- Replay by topic/partition/offset
- Re-publish to original topic without payload mutation
- Full audit trail

**Important**

- Replay is a human decision, not automatic

---

## Event-Driven Saga Choreography

- Choreography (no central coordinator)
- Services react to events and own decisions
- Failures are isolated and handled locally

---

## Kafka, Retry & DLT Strategy

**Kafka**

- Single integration backbone, at-least-once delivery, replayable history

**Retry**

- Spring Kafka DefaultErrorHandler
- Offset-aware, non-blocking, backoff-based

**Dead Letter Topics (DLT)**

- Used only for technical failures
- Payload preserved, failure metadata in headers
- Safe for audited replay

---

## Security Model

- JWT RS256 everywhere, JWKS validation in all services
- Role-based access control
- ADMIN-only operational endpoints
- Each service acts as a resource server
- No shared secrets

---

## Idempotency Strategy

Idempotency enforced at multiple layers:

- HTTP: Idempotency-Key
- Kafka: consumer-side deduplication logic
- Database: unique constraints
- Ledger: immutable writes

Prevents duplicate orders, double payments, corrupted saga state.

---

## Technology Stack

 Layer         | Technology                     
---------------|--------------------------------
 Language      | Java 25                        
 Framework     | Spring Boot 4                  
 Gateway       | Spring Cloud Gateway (WebFlux) 
 Messaging     | Apache Kafka                   
 Security      | Spring Security + JWT          
 Persistence   | MySQL                          
 Cache         | Redis                          
 Retry         | Spring Kafka                   
 Observability | Micrometer                     
 Serialization | JSON (Jackson)                 

---

## Important Note on Java 25 & Spring Boot 4

This project targets Java 25 and Spring Boot 4 to validate architectural correctness and failure-handling semantics (
Kafka, DLT, retry, saga) without coupling to experimental APIs. The goal is correctness and reliability over showcasing
bleeding-edge framework features.

---

## What This Project Proves

- Framework upgrades do not require architecture rewrites
- Reliability comes from design, not annotations
- DLT and replay tooling are first-class citizens
- Observability beats ad-hoc logging
- Failure handling must be explicit and testable

---

## Prerequisites

- **Java 25** - Temurin or compatible JDK
- **Docker** & **Docker Compose** - For infrastructure (Kafka, MySQL, Redis)
- **Gradle 8.x** - Included via Gradle Wrapper

---

## Building the Project

Each microservice can be built independently using Gradle:

```bash
# Build a specific service
cd order-service
./gradlew build

# Or build from root (requires navigating to each service)
for service in api-gateway auth-service dlt-replay-service notification-service order-service payment-service; do
    echo "Building $service..."
    (cd $service && ./gradlew build)
done
```

---

## Running Tests

Tests are configured to use embedded dependencies (H2 for databases, EmbeddedKafka for messaging):

```bash
# Run tests for a specific service
cd order-service
./gradlew test

# Run tests with coverage
./gradlew test jacocoTestReport
```

**Test Configuration Highlights:**

- **Database Tests**: Use H2 in-memory database via `@TestPropertySource`
- **Kafka Tests**: Use `@EmbeddedKafka` for integration testing
- **Security Tests**: Mock JWT issuer URIs for OAuth2 resource server tests

---

## How to Run

> **📖 Detailed Infrastructure Guide**: See [INFRASTRUCTURE.md](INFRASTRUCTURE.md) for comprehensive documentation on managing Kafka, MySQL, Redis, and troubleshooting.

### 1. Start Infrastructure

```bash
# Start Kafka, Zookeeper, MySQL, and Redis
docker-compose up -d

# Verify services are running (should show "healthy" status)
docker-compose ps

# View logs if needed
docker-compose logs -f
```

**Infrastructure Components:**
- **Kafka**: Event streaming (Port 9092)
- **Kafka UI**: Management interface (Port 8090)
- **MySQL**: Database (Port 3306)
- **Redis**: Cache (Port 6379)
- **Redis Commander**: Redis UI (Port 8081)
- **Zookeeper**: Kafka coordination (Port 2181)

### 2. Start Services

Run services in the following order to ensure dependencies are available:

```bash
# 1. Auth Service (Port 8096)
cd auth-service
./gradlew bootRun

# 2. API Gateway (Port 8080)
cd api-gateway
./gradlew bootRun

# 3. Order Service (Port 8097)
cd order-service
./gradlew bootRun

# 4. Payment Service (Port 8098)
cd payment-service
./gradlew bootRun

# 5. Notification Service (Port 8099)
cd notification-service
./gradlew bootRun

# 6. DLT Replay Admin Service (Port 8088)
cd dlt-replay-service
./gradlew bootRun
```

### 3. Service Ports

| Service | Port | Description |
|---------|------|-------------|
| API Gateway | 8080 | Main entry point |
| Auth Service | 8096 | JWT token issuance & JWKS |
| Order Service | 8097 | Order management & saga initiation |
| Payment Service | 8098 | Payment processing |
| Notification Service | 8099 | Event-driven notifications |
| DLT Replay Service | 8088 | Admin DLT replay tool |

### 4. Health Checks

Each service exposes actuator endpoints:

```bash
# Check service health
curl http://localhost:8097/actuator/health
curl http://localhost:8098/actuator/health
```

---

## CI/CD

This project uses **GitHub Actions** for continuous integration:

- **Workflow**: `.github/workflows/gradle.yml`
- **Build Strategy**: Matrix build for all 6 microservices in parallel
- **Test Execution**: Runs unit and integration tests with embedded dependencies
- **Dependency Scanning**: Automated dependency graph submission via Dependabot

### Workflow Features

- ✅ Parallel builds for faster feedback
- ✅ Independent service validation
- ✅ Automated dependency updates
- ✅ Build caching for improved performance

### Build Status

Check the [Actions tab](https://github.com/hendisantika/microservices-sample1/actions) for the latest build status.

---

## Project Structure

```
microservices-sample1/
├── api-gateway/           # Spring Cloud Gateway (Reactive)
├── auth-service/          # JWT RS256 token service
├── dlt-replay-service/    # Admin DLT replay tool
├── notification-service/  # Event consumer for notifications
├── order-service/         # Saga initiator & order management
├── payment-service/       # Payment processing & ledger
├── .github/
│   └── workflows/
│       └── gradle.yml     # CI/CD pipeline
└── docker-compose.yml     # Infrastructure setup
```

---

For operational details (configuration, schemas, deployment, DLT replay procedures), consult each service's subdirectory
README and the operational admin docs in this repository.