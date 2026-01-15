-- ==========================================
-- Microservices Database Initialization
-- ==========================================

-- Ensure microservices database exists
CREATE DATABASE IF NOT EXISTS microservices
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE microservices;

-- Grant permissions to microservice user
GRANT ALL PRIVILEGES ON microservices.* TO 'microservice_user'@'%';
FLUSH PRIVILEGES;

-- ==========================================
-- Note: Individual service schemas are
-- managed by Flyway/Liquibase migrations
-- or JPA DDL auto-creation
-- ==========================================
