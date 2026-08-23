# Spring Recipe Application - Multi-Environment & PostgreSQL (`spring-recipe-jpa-postgres`)

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Java Version](https://img.shields.io/badge/Java-21-blue.svg)](pom.xml)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.3-brightgreen.svg)](pom.xml)
[![Database](https://img.shields.io/badge/Database-PostgreSQL%2016%20%2F%20H2-blue.svg)](src/main/resources/sql-scripts)

An enterprise reference implementation building upon Stage 1 by introducing multi-environment configuration profiles (`default`, `dev`, `prod`), persistent **PostgreSQL 16** database integration, HikariCP connection pool hardening, and containerized multi-tier orchestration.

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
                       | In-Memory H2  |  | Postgres Dev |  | Postgres Prod |
                       | Auto-schema   |  | DDL: update  |  | DDL: update   |
                       | Bootstrap All |  | Seed Lookups |  | Seed Lookups  |
                       +---------------+  +--------------+  +---------------+
```

### Key Production Concepts Demonstrated
1. **Environment Segregation**:
   - `default`: In-memory H2 with full sample recipe bootstrap for rapid local iteration.
   - `dev`: PostgreSQL (`recipe_dev`) with schema update and debug SQL logging.
   - `prod`: PostgreSQL (`recipe_prod`) with hardened Hikari connection pool limits.
2. **Connection Pool Optimization (HikariCP)**: Explicit pool tuning parameters (`maximum-pool-size`, `minimum-idle`, `connection-timeout`, `leak-detection-threshold`).
3. **Externalized 12-Factor Configuration**: Environment variables (`DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD`) with safe defaults.
4. **Container Healthcheck Synchronization**: Docker Compose PostgreSQL 16 service orchestrated via health checks (`pg_isready`) before launching the application.

---

## 🚀 Getting Started

### Prerequisites
- **JDK**: Java 21
- **Maven**: 3.9+ (or use the included `./mvnw`)
- **Docker & Docker Compose**

---

### Local Execution Options

#### Option A: Run with Default In-Memory H2 Profile
```bash
# PowerShell
.\mvnw.cmd clean spring-boot:run
```

#### Option B: Run with Dev Profile (Requires local PostgreSQL)
```bash
.\mvnw.cmd clean spring-boot:run -Dspring-boot.run.profiles=dev
```

#### Option C: Run with Prod Profile
```bash
.\mvnw.cmd clean spring-boot:run -Dspring-boot.run.profiles=prod
```

---

### 🐳 Full Stack Docker Deployment

Spin up both PostgreSQL 16 (with auto-provisioned databases/roles) and the Spring Boot application in synchronized containers:

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
