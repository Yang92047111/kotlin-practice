# 📦 ConnectProtocal Module

## 📘 Description

The ConnectProtocal module serves as the central definition hub for cross-service communication contracts using multiple protocols:

- 🌐 **HTTP (REST)**: DTOs and API models for RESTful communication
- 🔄 **WebSocket**: Event models for bi-directional real-time messaging
- ✉️ **AMQP**: Message structures for publish/subscribe or queue-based patterns
- 🔗 **gRPC**: Protobuf service definitions and generated Kotlin stubs

This module enables consistent and version-controlled API definitions, decoupling transport-specific logic from business implementations.

---

## 🛠️ Implementation Status

- [x] Define shared DTOs for REST & WebSocket
- [x] Define AMQP message classes (event model)
- [x] Create `.proto` files for gRPC
- [x] Configure `protobuf-maven-plugin` for gRPC stub generation
- [x] Add comprehensive unit tests for serialization/deserialization
- [x] Add Jackson configuration for JSON handling
- [x] Add validation annotations for DTOs
- [ ] Add example usage documentation for each protocol
- [ ] Add backward compatibility testing

---

## � MavDen Dependencies

The module includes dependencies for:

- **Kotlin Standard Library**: Core Kotlin support
- **Jackson**: JSON serialization for HTTP & WebSocket
- **Jakarta Validation**: Bean validation annotations
- **gRPC & Protobuf**: Protocol buffer support and stub generation
- **JUnit 5 & AssertJ**: Comprehensive testing framework

---

## � Quick Start

### Build Module

```bash
# Build the module
mvn clean compile -pl ConnectProtocal

# Or using the Makefile
make build-protocol
```

### Generate gRPC Stubs

```bash
# Generate protobuf and gRPC stubs
mvn protobuf:compile protobuf:compile-custom -pl ConnectProtocal

# Or using the Makefile (includes build)
make build-protocol
```

### Run Tests

```bash
# Run all tests
mvn test -pl ConnectProtocal

# Or using the Makefile
make test-protocol
```

---

## 🧪 Unit Test Coverage

**Test Focus:**

- DTO serialization/deserialization (HTTP & WebSocket)
- AMQP message transformation and envelope handling
- Event polymorphism and type handling
- JSON schema validation

**Framework:** JUnit 5 + Jackson + AssertJ
**Coverage:** Comprehensive model integrity testing

---

## 📁 Module Structure

```bash
ConnectProtocal/
├── pom.xml                          # Maven configuration with protobuf plugin
├── src/
│   ├── main/
│   │   ├── kotlin/
│   │   │   └── com/example/spec/
│   │   │       ├── dto/             # HTTP/WebSocket DTOs
│   │   │       │   └── UserDto.kt   # User data transfer objects
│   │   │       ├── events/          # WebSocket event models
│   │   │       │   └── UserEvent.kt # Real-time user events
│   │   │       └── amqp/            # AMQP message contracts
│   │   │           └── UserMessage.kt # Pub/sub message schema
│   │   └── proto/
│   │       └── user.proto           # gRPC service definitions
│   └── test/
│       └── kotlin/
│           └── com/example/spec/
│               ├── dto/
│               │   └── UserDtoTest.kt
│               ├── events/
│               │   └── UserEventTest.kt
│               └── amqp/
│                   └── UserMessageTest.kt
```

---

## 🔗 Protocol Coverage

| Protocol      | Implementation                  | Directory | Purpose                          |
| ------------- | ------------------------------- | --------- | -------------------------------- |
| **HTTP**      | Kotlin DTOs with validation     | `dto/`    | REST API request/response models |
| **WebSocket** | Event classes with polymorphism | `events/` | Real-time event definitions      |
| **AMQP**      | Message classes with routing    | `amqp/`   | Pub/sub message schema           |
| **gRPC**      | Protobuf definitions            | `proto/`  | RPC services and data contracts  |

---

## 🎯 Key Features

### HTTP/REST DTOs

- Validation annotations (Jakarta Bean Validation)
- JSON serialization with Jackson
- Request/response wrapper classes
- Error handling models

### WebSocket Events

- Polymorphic event hierarchy
- Type-safe event handling
- Real-time message wrappers
- Event metadata support

### AMQP Messages

- Message envelope patterns
- Routing key conventions
- Message properties handling
- Correlation ID support

### gRPC Services

- Comprehensive user service definition
- Streaming support for events
- Error handling patterns
- Pagination support

---

## 🔧 Usage Examples

### HTTP DTO Usage

```kotlin
val createRequest = CreateUserRequest(
    username = "johndoe",
    email = "john@example.com",
    fullName = "John Doe"
)

val response = ApiResponse(
    success = true,
    message = "User created successfully",
    data = userDto
)
```

### WebSocket Event Usage

```kotlin
val event = UserCreatedEvent(
    eventId = UUID.randomUUID().toString(),
    userId = 1L,
    username = "johndoe",
    email = "john@example.com",
    fullName = "John Doe"
)

val message = WebSocketMessage(
    type = "USER_EVENT",
    payload = event
)
```

### AMQP Message Usage

```kotlin
val message = UserCreatedMessage(
    messageId = UUID.randomUUID().toString(),
    userId = 1L,
    username = "johndoe",
    email = "john@example.com",
    fullName = "John Doe"
)

val envelope = MessageEnvelope(
    headers = mapOf("source" to "user-service"),
    properties = MessageProperties(),
    body = message
)
```

---

## 📜 License

This module is part of the kotlin-practice project and follows the same [MIT License](../LICENSE).

---

## 🤝 Contributing

When adding new protocol definitions:

1. Follow the existing package structure
2. Add comprehensive unit tests
3. Update this README with new features
4. Ensure backward compatibility
5. Add validation where appropriate

---

## 🔄 Integration

This module is designed to be imported by other services that need to communicate using these protocols. Services can depend on this module to ensure consistent message formats across the entire system.
