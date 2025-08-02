# Oracle Trigger CRUD API

This module demonstrates a Kotlin-based REST API with Oracle Database integration, featuring database triggers and comprehensive transaction management. Built with Spring Boot and following clean architecture principles.

## Features

- **CRUD Operations**: The API supports Create, Read, Update, and Delete operations on the database.
- **Database Triggers**: The application includes triggers that log changes to a history table whenever data is inserted or updated.
- **Transaction Management**: The application ensures that all database operations are transactional, with rollback capabilities in case of failures.
- **Unit Testing**: The project uses JUnit 5 and Testcontainers for unit testing, ensuring that the API behaves as expected.

## Project Structure

```
OracleTrigger/
├── src/main/kotlin/
│   ├── UserCrudApplication.kt      # Main Spring Boot application
│   ├── controller/
│   │   └── UserController.kt       # REST endpoints
│   ├── service/
│   │   └── UserService.kt          # Business logic
│   ├── repository/
│   │   └── UserRepo.kt             # Data access layer
│   ├── model/
│   │   └── User.kt                 # Entity model
│   └── config/
│       └── DatabaseConfig.kt       # Database configuration
├── src/main/resources/
│   ├── application.properties      # App configuration
│   └── db/
│       ├── schema.sql              # Database schema with triggers
│       └── data.sql                # Sample data
├── src/test/kotlin/
│   └── controller/
│       └── UserControllerTest.kt   # Integration tests
└── pom.xml
```

## Setup Instructions

1. **Prerequisites**:
   - Oracle Database (local or remote)
   - Java 17+
   - Maven 3.8+

2. **Configure Database**:
   Update `src/main/resources/application.properties` with your Oracle connection details:
   ```properties
   spring.datasource.url=jdbc:oracle:thin:@localhost:1521:xe
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. **Build and Run**:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Initialize Database**:
   The application will automatically execute `schema.sql` and `data.sql` on startup.

5. **Run Tests**:
   ```bash
   mvn test
   ```

## API Endpoints

The application provides RESTful endpoints for user management:

- `GET /users` - Retrieve all users
- `GET /users/{id}` - Retrieve user by ID
- `POST /users` - Create new user
- `PUT /users/{id}` - Update existing user
- `DELETE /users/{id}` - Delete user

All database changes are automatically logged to a history table via Oracle triggers.

## License

This project is licensed under the MIT License. See the LICENSE file for more details.