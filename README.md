# Spring Recipe Application - Stage 2 (Multi-Environment & Production RDBMS)

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Java Version](https://img.shields.io/badge/Java-8%20%2F%2017%20%2F%2021-blue.svg)](pom.xml)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.0.8.RELEASE-brightgreen.svg)](pom.xml)
[![Database](https://img.shields.io/badge/Database-MySQL%208%20%2F%20H2-orange.svg)](src/main/resources/sql-scripts)

An enterprise reference implementation building upon Stage 1 by introducing multi-environment configuration profiles (`default`, `dev`, `prod`), persistent MySQL database integration, HikariCP connection pool hardening, and containerized multi-tier orchestration.

---

## 🏛 Multi-Profile Architecture

```
                                  +-----------------------------+
                                  |     Spring Boot Profile     |
                                  +-----------------------------+
                                         /       |       \
                                        /        |        \
                       +---------------+  +------+-------+  +---------------+
                       |  'default'    |  |    'dev'     |  |    'prod'     |
                       +---------------+  +--------------+  +---------------+
                               |                 |                  |
                               v                 v                  v
                       +---------------+  +--------------+  +---------------+
                       | In-Memory H2  |  | MySQL (Dev)  |  | MySQL (Prod)  |
                       | Auto-schema   |  | DDL: update  |  | DDL: validate |
                       | Bootstrap All |  | Seed Lookups |  | Seed Lookups  |
                       +---------------+  +--------------+  +---------------+
```

### Key Production Concepts Demonstrated
1. **Environment Segregation**:
   - `default`: In-memory H2 with full sample recipe bootstrap for rapid local iteration.
   - `dev`: MySQL (`recipe_dev`) with schema update and debug SQL logging.
   - `prod`: MySQL (`recipe_prod`) with strict schema validation (`ddl-auto: validate`), disabled debug logs, and hardened Hikari connection pool limits.
2. **Connection Pool Optimization (HikariCP)**: Explicit pool tuning parameters (`maximum-pool-size`, `minimum-idle`, `connection-timeout`, `leak-detection-threshold`).
3. **Externalized 12-Factor Configuration**: Environment variables (`DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD`) with safe defaults.
4. **Container Healthcheck Synchronization**: Docker Compose services orchestrated via health checks to prevent application startup before the database is ready.

---

## 🚀 Getting Started

### Prerequisites
- **JDK**: Java 8, 17, or 21
- **Maven**: 3.6+ (or use the included `./mvnw`)
- **Docker & Docker Compose**

---

### Local Execution Options

#### Option A: Run with Default In-Memory H2 Profile
```bash
# PowerShell
.\mvnw.cmd clean spring-boot:run
```

#### Option B: Run with Dev Profile (Requires local MySQL running)
```bash
.\mvnw.cmd clean spring-boot:run -Dspring-boot.run.profiles=dev
```

#### Option C: Run with Prod Profile
```bash
.\mvnw.cmd clean spring-boot:run -Dspring-boot.run.profiles=prod
```

---

### 🐳 Full Stack Docker Deployment

Spin up both the MySQL 8 database (with auto-provisioned schema and users) and the Spring Boot application in synchronized containers:

```bash
# Build and run the full stack
docker-compose up --build -d

# Inspect health and logs
docker-compose ps
docker-compose logs -f spring-recipe-app

# Tear down and clean volumes when finished
docker-compose down -v
```

---

## 🧪 Testing

```bash
# Run unit and integration tests
.\mvnw.cmd clean test
```

---

## 📄 License
This project is licensed under the [MIT License](LICENSE).
