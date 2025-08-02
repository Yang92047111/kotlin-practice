# Testing Guide

This document provides comprehensive information about testing in the Kotlin Practice Projects.

## Overview

Each module includes comprehensive unit tests demonstrating different testing approaches:

- **withcontext-coroutines**: Coroutines testing with `kotlinx-coroutines-test`
- **OracleTrigger**: Spring Boot testing with Mockito
- **Testcontainers**: Integration testing with Testcontainers

## Test Structure

```
kotlin-practice/
├── withcontext-coroutines/
│   └── src/test/kotlin/
│       ├── WithContextDemoTest.kt           # Unit tests for coroutines
│       └── WithContextIntegrationTest.kt    # Integration tests
├── OracleTrigger/
│   └── src/test/kotlin/
│       ├── controller/UserControllerTest.kt # Controller layer tests
│       ├── service/UserServiceTest.kt       # Service layer tests
│       └── repository/UserRepositoryTest.kt # Repository layer tests
└── Testcontainers/
    └── src/test/kotlin/com/practice/
        ├── controller/NoteControllerTest.kt # Controller tests
        └── service/NoteServiceTest.kt       # Service tests
```

## Running Tests

### All Tests
```bash
make test
# or
mvn test
```

### Individual Modules
```bash
# Coroutines tests
make test-coroutines
cd withcontext-coroutines && mvn test

# Oracle tests
make test-oracle
cd OracleTrigger && mvn test

# Testcontainers tests
make test-testcontainers
cd Testcontainers && mvn test
```

## Test Categories

### 1. Unit Tests

**Purpose**: Test individual components in isolation

**Technologies Used**:
- JUnit 5
- Mockito Kotlin
- Spring Boot Test

**Examples**:
- `UserServiceTest`: Tests business logic with mocked dependencies
- `NoteServiceTest`: Tests service layer with repository mocks
- `WithContextDemoTest`: Tests coroutine behavior

### 2. Integration Tests

**Purpose**: Test component interactions and real system behavior

**Technologies Used**:
- Spring Boot Test
- Testcontainers
- kotlinx-coroutines-test

**Examples**:
- `UserControllerTest`: Tests REST endpoints with mocked services
- `NoteControllerTest`: Tests HTTP request/response handling
- `WithContextIntegrationTest`: Tests complete coroutine flows

### 3. Container Tests (Testcontainers Module)

**Purpose**: Test with real database instances

**Technologies Used**:
- Testcontainers
- MySQL containers
- Oracle containers

**Benefits**:
- Real database behavior
- Isolated test environments
- Consistent across different machines

## Test Patterns Demonstrated

### 1. Mocking with Mockito Kotlin

```kotlin
@BeforeEach
fun setUp() {
    userRepository = mock()
    restTemplate = mock()
    userService = UserService(userRepository, restTemplate)
}

@Test
fun `create user successfully`() {
    // Given
    val user = User(name = "John Doe", email = "john@example.com")
    whenever(restTemplate.postForEntity(any<String>(), any(), eq(String::class.java)))
        .thenReturn(ResponseEntity("Success", HttpStatus.OK))

    // When
    val result = userService.create(user)

    // Then
    assertEquals(user, result)
    verify(userRepository).insert(user)
}
```

### 2. Spring Boot Web Testing

```kotlin
@WebMvcTest(UserController::class)
class UserControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var userService: UserService

    @Test
    fun `create user returns 201 CREATED`() {
        mockMvc.perform(
            post("/api/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.name").value("John Doe"))
    }
}
```

### 3. Coroutines Testing

```kotlin
@Test
fun `test withContext switches to IO dispatcher`() = runTest {
    val result = withContext(Dispatchers.IO) {
        delay(100) // Simulate IO work
        "Result from IO context"
    }
    
    assertEquals("Result from IO context", result)
}
```

## Test Coverage

Each module aims for comprehensive test coverage:

### withcontext-coroutines
- ✅ Context switching behavior
- ✅ Exception handling
- ✅ Concurrent operations
- ✅ Performance characteristics
- ✅ Integration flows

### OracleTrigger
- ✅ Controller endpoints (CRUD operations)
- ✅ Service business logic
- ✅ Repository data access
- ✅ Error handling
- ✅ Transaction behavior

### Testcontainers
- ✅ REST API endpoints
- ✅ Service layer logic
- ✅ Database integration (via Testcontainers)
- ✅ Error scenarios

## Best Practices Demonstrated

1. **Test Isolation**: Each test is independent and can run in any order
2. **Clear Test Names**: Descriptive test method names using backticks
3. **Given-When-Then**: Clear test structure
4. **Mocking Strategy**: Mock external dependencies, test real logic
5. **Edge Cases**: Test both success and failure scenarios
6. **Performance Testing**: Basic performance characteristics testing

## Continuous Integration

The tests are designed to run in CI environments:

```bash
# CI pipeline simulation
make ci
# Runs: clean → install → test → package
```

## Troubleshooting

### Common Issues

1. **Docker not available**: Testcontainers tests will fail
   - Solution: Install Docker and ensure it's running

2. **Oracle database connection**: OracleTrigger integration tests
   - Solution: Configure test database or use Testcontainers Oracle

3. **Port conflicts**: Spring Boot tests
   - Solution: Tests use random ports by default

### Debug Mode

Run tests with debug output:
```bash
mvn test -X
```

### Test Reports

Generate test reports:
```bash
make test-report
# Reports available in target/site/surefire-report.html
```

## Future Enhancements

- [ ] Add mutation testing with PIT
- [ ] Add contract testing with Pact
- [ ] Add performance benchmarking with JMH
- [ ] Add property-based testing with QuickCheck
- [ ] Add test data builders
- [ ] Add custom test annotations