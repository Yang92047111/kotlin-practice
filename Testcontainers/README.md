# Testcontainers Integration Demo

This module demonstrates integration testing with Testcontainers using a Spring Boot Notes CRUD application. It showcases how to write reliable integration tests that spin up real database containers.

## Features

- **CRUD Operations**: Complete Create, Read, Update, Delete functionality for notes
- **RESTful API**: Clean REST endpoints following best practices
- **Testcontainers Integration**: Real database testing with containerized MySQL
- **Spring Boot**: Modern Spring Boot application structure
- **Reliable Testing**: Integration tests that don't depend on external database setup

## Project Structure

```
Testcontainers/
├── src/main/kotlin/com/practice/
│   ├── [Application classes]           # Spring Boot application
│   ├── controller/                     # REST controllers
│   ├── service/                        # Business logic
│   ├── repository/                     # Data access
│   └── model/                          # Entity models
├── src/main/resources/
│   ├── application.properties          # Application configuration
│   └── schema.sql                      # Database schema
├── src/test/kotlin/com/practice/
│   └── [Test classes]                  # Testcontainers integration tests
└── pom.xml
```

## Setup Instructions

1. **Prerequisites:**
   - Java 17+
   - Maven 3.8+
   - Docker (for Testcontainers)

2. **Build the project:**
   ```bash
   mvn clean install
   ```

3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the API:**
   - Application runs on `http://localhost:8080`
   - Use tools like Postman, curl, or your browser to test endpoints

5. **Key Advantage:**
   No need to manually set up MySQL - the application uses an embedded H2 database for development, while tests use real MySQL containers via Testcontainers.

## Testing with Testcontainers

This module showcases the power of Testcontainers for integration testing:

- **Real Database Testing**: Tests run against actual MySQL containers
- **Isolated Test Environment**: Each test gets a fresh database instance
- **No External Dependencies**: No need to set up MySQL locally for testing
- **Reliable CI/CD**: Tests work consistently across different environments

**Run tests:**
```bash
mvn test
```

The tests will automatically:
1. Start a MySQL container
2. Run database migrations
3. Execute integration tests
4. Clean up containers after completion

## License

This project is licensed under the MIT License.