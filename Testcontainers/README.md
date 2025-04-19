# Notes CRUD Application

This is a simple Notes CRUD application built with Kotlin and Spring Boot. It provides RESTful endpoints for managing notes and uses MySQL as the database.

## Features

- Create, Read, Update, and Delete (CRUD) operations for notes.
- RESTful API endpoints for easy integration.
- Unit tests using Testcontainers for reliable testing.

## Project Structure

```
notes-crud-app
├── src
│   ├── main
│   │   ├── kotlin
│   │   │   └── com
│   │   │       └── example
│   │   │           └── notes
│   │   │               ├── NotesApplication.kt
│   │   │               ├── controller
│   │   │               │   └── NotesController.kt
│   │   │               ├── model
│   │   │               │   └── Note.kt
│   │   │               ├── repository
│   │   │               │   └── NotesRepository.kt
│   │   │               └── service
│   │   │                   └── NotesService.kt
│   │   └── resources
│   │       ├── application.properties
│   │       └── db
│   │           └── migration
│   │               └── V1__Create_Notes_Table.sql
│   └── test
│       ├── kotlin
│       │   └── com
│       │       └── example
│       │           └── notes
│       │               ├── NotesControllerTest.kt
│       │               └── TestcontainersConfig.kt
│       └── resources
├── pom.xml
└── README.md
```

## Setup Instructions

1. **Clone the repository:**
   ```
   git clone <repository-url>
   cd notes-crud-app
   ```

2. **Configure the database:**
   - Ensure you have MySQL installed and running.
   - Create a database for the application.
   - Update the `src/main/resources/application.properties` file with your database connection details.

3. **Build the project:**
   ```
   mvn clean install
   ```

4. **Run the application:**
   ```
   mvn spring-boot:run
   ```

5. **Access the API:**
   - The application will be available at `http://localhost:8080`.
   - Use tools like Postman or curl to interact with the RESTful endpoints.

## Testing

- Unit tests are provided for the `NotesController` using Testcontainers.
- To run the tests, use the following command:
  ```
  mvn test
  ```

## License

This project is licensed under the MIT License.