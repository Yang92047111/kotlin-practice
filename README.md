# 📘 Kotlin Practice Projects

This repository contains multiple Kotlin practice projects demonstrating different concepts and technologies.

## 🧱 Project Structure

The repository is a **Maven multi-module project** with the following modules:

```
kotlin-practice/
├── pom.xml                    # Parent POM
├── withcontext-coroutines/   # Kotlin Coroutines with withContext
├── OracleTrigger/           # Oracle Database CRUD with triggers
├── Testcontainers/          # Spring Boot CRUD with Testcontainers
└── ConnectProtocal/         # Multi-protocol API contracts (HTTP, WebSocket, AMQP, gRPC)
```

Each module focuses on a specific technology or concept.

---

## 🛠 Technologies Used

- **Kotlin 2.1.x**
- **Java 17**
- **Maven**
- **Spring Boot** (Testcontainers module)
- **Oracle Database** (OracleTrigger module)
- **Kotlin Coroutines** (withcontext-coroutines module)
- **Testcontainers** for integration testing
- **GitHub Actions** for CI/CD pipeline

---

## 📚 Modules Overview

### 🔄 withcontext-coroutines

Demonstrates Kotlin Coroutines with `withContext` for context switching between different dispatchers.

**Key concepts:**

- Context switching with `withContext(Dispatchers.IO)` and `Dispatchers.Default`
- Understanding structured concurrency
- Thread management in coroutines

### 🗄️ OracleTrigger

A Kotlin-based REST API with Oracle Database integration featuring database triggers and transaction management.

**Key features:**

- CRUD operations with RESTful endpoints
- Database triggers for audit logging
- Transaction management with rollback capabilities
- Clean architecture (Controller → Service → Repository)

### 🧪 Testcontainers

A Spring Boot Notes CRUD application demonstrating integration testing with Testcontainers.

**Key features:**

- Spring Boot REST API
- MySQL database integration
- Testcontainers for reliable integration testing
- Complete CRUD operations for notes management

### 📦 ConnectProtocal

A specification module defining shared API contracts across multiple communication protocols.

**Key features:**

- HTTP REST DTOs with validation
- WebSocket event models for real-time messaging
- AMQP message structures for pub/sub patterns
- gRPC protobuf definitions and generated stubs
- Cross-protocol consistency and version control

---

## 🚀 Quick Start

### 🔧 Prerequisites

- Java 17+
- Maven 3.8+
- Oracle Database (for OracleTrigger module)
- Docker (for Testcontainers module)

### � ️ Using the Makefile (Recommended)

This project includes a comprehensive Makefile for easy development:

```bash
# See all available commands
make help

# Build all modules
make build

# Run all tests
make test

# Run individual modules
make run-coroutines      # Kotlin Coroutines demo
make run-oracle          # Oracle CRUD API
make run-testcontainers  # Notes API with Testcontainers
make build-protocol      # Build ConnectProtocal specs and generate gRPC stubs

# Development setup
make dev-setup          # Check environment and install dependencies
```

### 🔃 Manual Maven Commands

**Build all modules:**

```bash
mvn clean install
```

**Run individual modules:**

**Coroutines Demo:**

```bash
cd withcontext-coroutines
mvn exec:java -Dexec.mainClass=WithContextDemoKt
```

**Oracle Trigger API:**

```bash
cd OracleTrigger
# Configure database connection in application.properties
mvn spring-boot:run
```

**Testcontainers Notes API:**

```bash
cd Testcontainers
mvn spring-boot:run
```

---

## 📁 Detailed Structure

```
kotlin-practice/
├── pom.xml                           # Parent POM
├── withcontext-coroutines/          # Coroutines module
│   ├── pom.xml
│   └── src/main/kotlin/
│       └── WithContextDemo.kt
├── OracleTrigger/                   # Oracle DB module
│   ├── pom.xml
│   ├── src/main/kotlin/
│   │   ├── controller/
│   │   ├── service/
│   │   ├── repository/
│   │   ├── model/
│   │   └── config/
│   └── src/main/resources/
│       ├── application.properties
│       └── db/
├── Testcontainers/                  # Testcontainers module
│   ├── pom.xml
│   ├── src/main/kotlin/
│   │   └── com/practice/
│   └── src/test/kotlin/
└── ConnectProtocal/                 # Protocol specs module
    ├── pom.xml
    ├── src/main/kotlin/
    │   └── com/example/spec/
    │       ├── dto/                 # HTTP/WebSocket DTOs
    │       ├── events/              # WebSocket events
    │       └── amqp/                # AMQP messages
    ├── src/main/proto/
    │   └── user.proto               # gRPC definitions
    └── src/test/kotlin/
```

---

## 🧪 Testing

Each module includes comprehensive unit tests demonstrating different testing approaches:

### Test Coverage

- **withcontext-coroutines**: Coroutines testing with `kotlinx-coroutines-test`
- **OracleTrigger**: Spring Boot testing with Mockito and web layer tests
- **Testcontainers**: Integration testing with real database containers
- **ConnectProtocal**: DTO serialization, event handling, and protocol contract testing

### Running Tests

```bash
# All tests
make test

# Individual modules
make test-coroutines
make test-oracle
make test-testcontainers
make test-protocol
```

For detailed testing information, see [TESTING.md](TESTING.md).

---

## 🔄 CI/CD Pipeline

This project includes a comprehensive GitHub Actions CI/CD pipeline:

### Automated Workflows
- **🚀 CI Pipeline**: Runs on every push and PR
  - Multi-module build and test
  - Parallel test execution
  - Security scanning
  - Artifact generation

- **🌙 Nightly Build**: Comprehensive testing and maintenance
  - Extended integration tests
  - Performance testing
  - Dependency vulnerability scans

- **📦 Release Pipeline**: Automated releases on git tags
  - Artifact packaging
  - Changelog generation
  - GitHub release creation

- **🔧 Maintenance**: Weekly automated maintenance
  - Dependency update checks
  - Security audits
  - Code quality analysis

### Status Badges
[![CI Pipeline](https://github.com/YOUR_USERNAME/kotlin-practice/actions/workflows/ci.yml/badge.svg)](https://github.com/YOUR_USERNAME/kotlin-practice/actions/workflows/ci.yml)
[![Nightly Build](https://github.com/YOUR_USERNAME/kotlin-practice/actions/workflows/nightly.yml/badge.svg)](https://github.com/YOUR_USERNAME/kotlin-practice/actions/workflows/nightly.yml)

For detailed CI/CD information, see [.github/README.md](.github/README.md).

---

## ✅ Current Status & Future Ideas

**Completed:**

- [x] Kotlin Coroutines with `withContext`
- [x] Oracle Database CRUD with triggers
- [x] Spring Boot with Testcontainers integration
- [x] Multi-protocol API contracts (HTTP, WebSocket, AMQP, gRPC)
- [x] Comprehensive unit tests for all modules
- [x] Makefile for easy development workflow

**Future Ideas:**

- [ ] Kotlin Flow examples
- [ ] Structured concurrency with `supervisorScope`
- [ ] Exception handling in coroutines
- [ ] Java CompletableFuture comparison
- [ ] Performance benchmarking
- [ ] Integration tests with real databases

---

## 🤝 Contributing

Feel free to fork and open a PR with new exercises or improvements.

---

## 📜 License

[MIT License](./LICENSE)

---

## 🎯 Purpose

This repository serves as a practical learning playground for exploring various Kotlin technologies and patterns. Each module demonstrates real-world applications of different concepts, from coroutines to database integration and testing strategies.
