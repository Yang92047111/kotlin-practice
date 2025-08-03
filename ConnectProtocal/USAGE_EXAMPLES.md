# ConnectProtocal Usage Examples

This document provides practical examples of how to use the ConnectProtocal module across different communication protocols.

## HTTP/REST DTOs

### Creating and Validating User DTOs

```kotlin
import com.example.spec.dto.*
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import jakarta.validation.Validation

// Setup Jackson ObjectMapper
val objectMapper = ObjectMapper()
    .registerModule(KotlinModule.Builder().build())

// Create a user creation request
val createRequest = CreateUserRequest(
    username = "johndoe",
    email = "john@example.com",
    fullName = "John Doe"
)

// Serialize to JSON
val json = objectMapper.writeValueAsString(createRequest)
println("Create Request JSON: $json")

// Create API response
val userDto = UserDto(
    id = 1L,
    username = "johndoe",
    email = "john@example.com",
    fullName = "John Doe",
    createdAt = LocalDateTime.now()
)

val response = ApiResponse(
    success = true,
    message = "User created successfully",
    data = userDto
)

// Validation example
val validator = Validation.buildDefaultValidatorFactory().validator
val violations = validator.validate(createRequest)
if (violations.isNotEmpty()) {
    violations.forEach { println("Validation error: ${it.message}") }
}
```

## WebSocket Events

### Publishing User Events

```kotlin
import com.example.spec.events.*
import java.util.UUID

// Create different types of user events
val userCreatedEvent = UserCreatedEvent(
    eventId = UUID.randomUUID().toString(),
    userId = 1L,
    username = "johndoe",
    email = "john@example.com",
    fullName = "John Doe"
)

val userUpdatedEvent = UserUpdatedEvent(
    eventId = UUID.randomUUID().toString(),
    userId = 1L,
    updatedFields = mapOf(
        "email" to "john.doe@example.com",
        "fullName" to "John A. Doe"
    )
)

// Wrap in WebSocket message
val message = WebSocketMessage(
    type = "USER_EVENT",
    payload = userCreatedEvent
)

// Serialize for WebSocket transmission
val eventJson = objectMapper.writeValueAsString(message)
println("WebSocket Message: $eventJson")

// Handle polymorphic deserialization
val deserializedEvent = objectMapper.readValue(eventJson, WebSocketMessage::class.java)
when (val payload = deserializedEvent.payload) {
    is UserCreatedEvent -> println("User created: ${payload.username}")
    is UserUpdatedEvent -> println("User updated: ${payload.updatedFields}")
    // Handle other event types...
}
```

## AMQP Messages

### Publishing to Message Queue

```kotlin
import com.example.spec.amqp.*

// Create AMQP message
val userCreatedMessage = UserCreatedMessage(
    messageId = UUID.randomUUID().toString(),
    userId = 1L,
    username = "johndoe",
    email = "john@example.com",
    fullName = "John Doe",
    source = "user-service"
)

// Create message envelope with headers and properties
val headers = mapOf(
    "source" to "user-service",
    "version" to "1.0",
    "correlation-id" to UUID.randomUUID().toString()
)

val properties = MessageProperties(
    contentType = "application/json",
    deliveryMode = 2, // Persistent
    correlationId = headers["correlation-id"],
    timestamp = LocalDateTime.now()
)

val envelope = MessageEnvelope(
    headers = headers,
    properties = properties,
    body = userCreatedMessage
)

// Serialize for AMQP publishing
val amqpJson = objectMapper.writeValueAsString(envelope)
println("AMQP Message: $amqpJson")

// Example of routing key usage
println("Routing Key: ${userCreatedMessage.routingKey}") // "user.created"
```

## gRPC Services

### Using Generated gRPC Stubs

```kotlin
import com.example.spec.grpc.*
import io.grpc.ManagedChannelBuilder

// Create gRPC channel
val channel = ManagedChannelBuilder.forAddress("localhost", 9090)
    .usePlaintext()
    .build()

// Create service stub
val userService = UserServiceGrpc.newBlockingStub(channel)

// Create user request
val createUserRequest = CreateUserRequest.newBuilder()
    .setUsername("johndoe")
    .setEmail("john@example.com")
    .setFullName("John Doe")
    .build()

try {
    // Call gRPC service
    val response = userService.createUser(createUserRequest)
    
    when (response.resultCase) {
        CreateUserResponse.ResultCase.USER -> {
            val user = response.user
            println("User created: ${user.username} (ID: ${user.id})")
        }
        CreateUserResponse.ResultCase.ERROR -> {
            val error = response.error
            println("Error: ${error.message}")
        }
        else -> println("Unknown response")
    }
} finally {
    channel.shutdown()
}

// Stream user events
val streamRequest = StreamUserEventsRequest.newBuilder()
    .addUserIds(1L)
    .addEventTypes(UserEventType.USER_EVENT_TYPE_CREATED)
    .addEventTypes(UserEventType.USER_EVENT_TYPE_UPDATED)
    .build()

val eventStream = userService.streamUserEvents(streamRequest)
eventStream.forEach { event ->
    println("Received event: ${event.eventType} for user ${event.userId}")
    when (event.payloadCase) {
        UserEventMessage.PayloadCase.USER_CREATED -> {
            val payload = event.userCreated
            println("User created: ${payload.user.username}")
        }
        UserEventMessage.PayloadCase.USER_UPDATED -> {
            val payload = event.userUpdated
            println("User updated: ${payload.updatedFieldsList}")
        }
        // Handle other event types...
    }
}
```

## Cross-Protocol Integration

### Converting Between Protocols

```kotlin
// Convert HTTP DTO to AMQP Message
fun convertToAmqpMessage(userDto: UserDto): UserCreatedMessage {
    return UserCreatedMessage(
        messageId = UUID.randomUUID().toString(),
        userId = userDto.id ?: 0L,
        username = userDto.username,
        email = userDto.email,
        fullName = userDto.fullName
    )
}

// Convert HTTP DTO to WebSocket Event
fun convertToWebSocketEvent(userDto: UserDto): UserCreatedEvent {
    return UserCreatedEvent(
        eventId = UUID.randomUUID().toString(),
        userId = userDto.id ?: 0L,
        username = userDto.username,
        email = userDto.email,
        fullName = userDto.fullName
    )
}

// Convert HTTP DTO to gRPC Request
fun convertToGrpcRequest(createRequest: CreateUserRequest): com.example.spec.grpc.CreateUserRequest {
    return com.example.spec.grpc.CreateUserRequest.newBuilder()
        .setUsername(createRequest.username)
        .setEmail(createRequest.email)
        .setFullName(createRequest.fullName)
        .build()
}

// Example usage in a service
class UserService {
    fun createUser(request: CreateUserRequest): ApiResponse<UserDto> {
        // 1. Validate HTTP request
        val violations = validator.validate(request)
        if (violations.isNotEmpty()) {
            return ApiResponse(
                success = false,
                message = "Validation failed",
                data = null
            )
        }
        
        // 2. Create user (business logic)
        val userDto = UserDto(
            id = generateUserId(),
            username = request.username,
            email = request.email,
            fullName = request.fullName,
            createdAt = LocalDateTime.now()
        )
        
        // 3. Publish AMQP message
        val amqpMessage = convertToAmqpMessage(userDto)
        messagePublisher.publish("user.exchange", amqpMessage.routingKey, amqpMessage)
        
        // 4. Send WebSocket event
        val wsEvent = convertToWebSocketEvent(userDto)
        webSocketHandler.broadcast(wsEvent)
        
        // 5. Return HTTP response
        return ApiResponse(
            success = true,
            message = "User created successfully",
            data = userDto
        )
    }
}
```

## Testing Examples

### Unit Testing Protocol Contracts

```kotlin
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat

class ProtocolIntegrationTest {
    
    @Test
    fun `should maintain data consistency across protocols`() {
        // Given
        val originalData = CreateUserRequest(
            username = "testuser",
            email = "test@example.com",
            fullName = "Test User"
        )
        
        // When - Convert to different protocols
        val httpJson = objectMapper.writeValueAsString(originalData)
        val amqpMessage = convertToAmqpMessage(createUserDto(originalData))
        val wsEvent = convertToWebSocketEvent(createUserDto(originalData))
        val grpcRequest = convertToGrpcRequest(originalData)
        
        // Then - Verify data consistency
        assertThat(amqpMessage.username).isEqualTo(originalData.username)
        assertThat(wsEvent.email).isEqualTo(originalData.email)
        assertThat(grpcRequest.fullName).isEqualTo(originalData.fullName)
        
        // Verify serialization/deserialization
        val deserializedHttp = objectMapper.readValue(httpJson, CreateUserRequest::class.java)
        assertThat(deserializedHttp).isEqualTo(originalData)
    }
}
```

This module provides a consistent way to handle user data across all communication protocols in your microservices architecture, ensuring type safety and reducing the risk of data inconsistencies.