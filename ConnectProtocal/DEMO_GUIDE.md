# ConnectProtocal Demo Guide

This guide provides step-by-step instructions for running and understanding the ConnectProtocal module demonstrations.

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven 3.8+
- Terminal/Command Line access

### Run the Full Demo
```bash
make run-protocol-demo
```

This command will:
1. Build the ConnectProtocal module
2. Generate gRPC stubs from protobuf definitions
3. Run an interactive demonstration of all protocols

## 🎯 Demo Components

### 1. HTTP/REST Protocol Demo
**What it shows:**
- Creating user DTOs with validation
- JSON serialization/deserialization
- Request/response patterns
- Error handling structures

**Key Features:**
- `CreateUserRequest` - Input validation with Jakarta Bean Validation
- `UserDto` - Complete user representation with timestamps
- `ApiResponse<T>` - Generic response wrapper
- `UpdateUserRequest` - Partial update patterns

### 2. WebSocket Protocol Demo
**What it shows:**
- Real-time event broadcasting
- Polymorphic event handling
- Type-safe event serialization
- Event metadata management

**Key Features:**
- `UserCreatedEvent` - User creation notifications
- `UserUpdatedEvent` - Change tracking with field maps
- `UserStatusChangedEvent` - Status transition events
- `WebSocketMessage<T>` - Event envelope pattern

### 3. AMQP Protocol Demo
**What it shows:**
- Message queue patterns
- Routing key conventions
- Message envelope structures
- Correlation ID handling

**Key Features:**
- `UserCreatedMessage` - Pub/sub user creation
- `MessageEnvelope<T>` - Complete message wrapper
- `MessageProperties` - AMQP metadata handling
- Routing patterns: `user.created`, `user.updated`, etc.

### 4. gRPC Protocol Demo
**What it shows:**
- Service definition overview
- Generated stub information
- Streaming capabilities
- Type-safe RPC patterns

**Key Features:**
- `UserService` - Complete CRUD operations
- `StreamUserEvents` - Real-time event streaming
- Request/response patterns with error handling
- Pagination support for list operations

### 5. Cross-Protocol Integration Demo
**What it shows:**
- Data flow across all protocols
- Consistent data models
- Protocol conversion patterns
- Integration benefits

**Scenario:**
1. HTTP request received
2. Business logic processes request
3. HTTP response sent
4. WebSocket event broadcasted
5. AMQP message published
6. gRPC stream notification sent

## 🛠️ Additional Commands

### View Generated Files
```bash
make show-generated
```
Shows the structure and statistics of generated gRPC files.

### Build Only
```bash
make build-protocol
```
Builds the module and generates gRPC stubs without running the demo.

### Run Tests
```bash
make test-protocol
```
Runs comprehensive unit tests for all protocol contracts.

### Quick Module Overview
```bash
make demo-quick
```
Shows a quick overview of all modules in the project.

## 📊 Demo Output Explanation

### JSON Pretty Printing
The demo automatically formats JSON output for readability. You'll see:
- Proper indentation
- Field ordering
- Timestamp arrays (LocalDateTime serialization)
- Nested object structures

### Protocol-Specific Features

**HTTP/REST:**
- Validation error messages
- Response status indicators
- Timestamp tracking
- Partial update support

**WebSocket:**
- Event type discrimination
- Real-time timestamp generation
- Polymorphic payload handling
- Event correlation

**AMQP:**
- Routing key patterns
- Message persistence settings
- Correlation ID tracking
- Header metadata

**gRPC:**
- Service method listings
- Streaming operation descriptions
- Error handling patterns
- Type safety demonstrations

## 🎓 Learning Objectives

After running the demo, you should understand:

1. **Protocol Consistency**: How the same data model works across different protocols
2. **Type Safety**: How Kotlin's type system prevents data corruption
3. **Serialization**: How Jackson handles JSON conversion automatically
4. **Validation**: How Jakarta Bean Validation ensures data integrity
5. **Code Generation**: How protobuf generates type-safe gRPC code
6. **Integration Patterns**: How to convert between protocol formats

## 🔧 Customization

### Modify Demo Data
Edit `ConnectProtocal/src/main/kotlin/com/example/spec/demo/ProtocolDemo.kt`:
- Change user data in the `runDemo()` method
- Add new protocol demonstrations
- Customize output formatting

### Add New Protocols
1. Create new message classes in appropriate packages
2. Add serialization tests
3. Update the demo to showcase new protocols
4. Document usage patterns

### Extend gRPC Services
1. Modify `src/main/proto/user.proto`
2. Run `make build-protocol` to regenerate stubs
3. Update demo to show new service methods

## 📚 Next Steps

1. **Explore the Code**: Browse the source files to understand implementation details
2. **Run Tests**: Execute `make test-protocol` to see comprehensive test coverage
3. **Read Examples**: Check `USAGE_EXAMPLES.md` for detailed code examples
4. **Integration**: Use this module as a dependency in your own projects

## 🤝 Contributing

To add new features or protocols:
1. Follow the existing package structure
2. Add comprehensive tests
3. Update documentation
4. Extend the demo to showcase new features

The ConnectProtocal module demonstrates how to build maintainable, type-safe API contracts that work consistently across multiple communication protocols in modern microservices architectures.