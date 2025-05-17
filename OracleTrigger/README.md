# My Project

This project is a Kotlin-based application that utilizes Maven for dependency management and JDK 17. It connects to an Oracle Database and implements a RESTful API with CRUD operations. The architecture is organized into Controller, Service, and Repository layers, ensuring a clean separation of concerns.

## Features

- **CRUD Operations**: The API supports Create, Read, Update, and Delete operations on the database.
- **Database Triggers**: The application includes triggers that log changes to a history table whenever data is inserted or updated.
- **Transaction Management**: The application ensures that all database operations are transactional, with rollback capabilities in case of failures.
- **Unit Testing**: The project uses JUnit 5 and Testcontainers for unit testing, ensuring that the API behaves as expected.

## Project Structure

```
my-project
├── sub-module
│   ├── src
│   │   ├── main
│   │   │   ├── kotlin
│   │   │   │   ├── controller
│   │   │   │   │   └── ApiController.kt
│   │   │   │   ├── service
│   │   │   │   │   └── ApiService.kt
│   │   │   │   ├── repository
│   │   │   │   │   └── ApiRepository.kt
│   │   │   │   └── config
│   │   │   │       └── DatabaseConfig.kt
│   │   │   └── resources
│   │   │       ├── application.properties
│   │   │       └── db
│   │   │           ├── schema.sql
│   │   │           └── data.sql
│   │   └── test
│   │       ├── kotlin
│   │       │   └── ApiTests.kt
│   │       └── resources
│   ├── pom.xml
└── README.md
```

## Setup Instructions

1. **Clone the Repository**: 
   ```
   git clone <repository-url>
   cd my-project
   ```

2. **Build the Project**: 
   Use Maven to build the project:
   ```
   mvn clean install
   ```

3. **Configure Database**: 
   Update the `application.properties` file with your Oracle Database connection details.

4. **Run the Application**: 
   You can run the application using your preferred method (e.g., through an IDE or command line).

5. **Run Tests**: 
   Execute the unit tests to ensure everything is functioning correctly:
   ```
   mvn test
   ```

## Usage

Once the application is running, you can interact with the API through the defined endpoints for CRUD operations. Refer to the API documentation for specific endpoint details and request/response formats.

## License

This project is licensed under the MIT License. See the LICENSE file for more details.