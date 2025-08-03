package com.example.spec.demo

import com.example.spec.dto.*
import com.example.spec.events.*
import com.example.spec.amqp.*
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.time.LocalDateTime
import java.util.UUID

/**
 * Interactive demo showcasing ConnectProtocal multi-protocol capabilities
 */
class ProtocolDemo {
    
    private val objectMapper = ObjectMapper()
        .registerModule(KotlinModule.Builder().build())
        .registerModule(JavaTimeModule())
    
    fun runDemo() {
        println("🚀 ConnectProtocal Multi-Protocol Demo")
        println("=" * 50)
        
        // Demo data
        val userId = 1L
        val username = "johndoe"
        val email = "john@example.com"
        val fullName = "John Doe"
        
        demonstrateHttpProtocol(username, email, fullName)
        println()
        
        demonstrateWebSocketProtocol(userId, username, email, fullName)
        println()
        
        demonstrateAmqpProtocol(userId, username, email, fullName)
        println()
        
        demonstrateGrpcProtocol()
        println()
        
        demonstrateCrossProtocolIntegration(userId, username, email, fullName)
        
        println("✅ Demo completed successfully!")
    }
    
    private fun demonstrateHttpProtocol(username: String, email: String, fullName: String) {
        println("🌐 HTTP/REST Protocol Demo")
        println("-" * 30)
        
        // Create user request
        val createRequest = CreateUserRequest(
            username = username,
            email = email,
            fullName = fullName
        )
        
        val requestJson = objectMapper.writeValueAsString(createRequest)
        println("📤 HTTP Request:")
        println(requestJson.prettyPrint())
        
        // Simulate user creation response
        val userDto = UserDto(
            id = 1L,
            username = username,
            email = email,
            fullName = fullName,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        
        val response = ApiResponse(
            success = true,
            message = "User created successfully",
            data = userDto
        )
        
        val responseJson = objectMapper.writeValueAsString(response)
        println("📥 HTTP Response:")
        println(responseJson.prettyPrint())
        
        // Update request demo
        val updateRequest = UpdateUserRequest(
            email = "john.doe@example.com",
            fullName = "John A. Doe"
        )
        
        val updateJson = objectMapper.writeValueAsString(updateRequest)
        println("🔄 HTTP Update Request:")
        println(updateJson.prettyPrint())
    }
    
    private fun demonstrateWebSocketProtocol(userId: Long, username: String, email: String, fullName: String) {
        println("🔄 WebSocket Protocol Demo")
        println("-" * 30)
        
        // User created event
        val createdEvent = UserCreatedEvent(
            eventId = UUID.randomUUID().toString(),
            userId = userId,
            username = username,
            email = email,
            fullName = fullName
        )
        
        val wsMessage = WebSocketMessage(
            type = "USER_EVENT",
            payload = createdEvent
        )
        
        val wsJson = objectMapper.writeValueAsString(wsMessage)
        println("📡 WebSocket Event (User Created):")
        println(wsJson.prettyPrint())
        
        // User updated event
        val updatedEvent = UserUpdatedEvent(
            eventId = UUID.randomUUID().toString(),
            userId = userId,
            updatedFields = mapOf(
                "email" to "john.doe@example.com",
                "fullName" to "John A. Doe"
            )
        )
        
        val updateWsMessage = WebSocketMessage(
            type = "USER_EVENT",
            payload = updatedEvent
        )
        
        val updateWsJson = objectMapper.writeValueAsString(updateWsMessage)
        println("📡 WebSocket Event (User Updated):")
        println(updateWsJson.prettyPrint())
        
        // Status changed event
        val statusEvent = UserStatusChangedEvent(
            eventId = UUID.randomUUID().toString(),
            userId = userId,
            oldStatus = "ACTIVE",
            newStatus = "SUSPENDED"
        )
        
        val statusWsMessage = WebSocketMessage(
            type = "USER_EVENT",
            payload = statusEvent
        )
        
        val statusWsJson = objectMapper.writeValueAsString(statusWsMessage)
        println("📡 WebSocket Event (Status Changed):")
        println(statusWsJson.prettyPrint())
    }
    
    private fun demonstrateAmqpProtocol(userId: Long, username: String, email: String, fullName: String) {
        println("✉️ AMQP Protocol Demo")
        println("-" * 30)
        
        // User created message
        val createdMessage = UserCreatedMessage(
            messageId = UUID.randomUUID().toString(),
            userId = userId,
            username = username,
            email = email,
            fullName = fullName,
            source = "user-service"
        )
        
        // Message envelope
        val headers = mapOf(
            "source" to "user-service",
            "version" to "1.0",
            "correlation-id" to UUID.randomUUID().toString(),
            "content-type" to "application/json"
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
            body = createdMessage
        )
        
        val amqpJson = objectMapper.writeValueAsString(envelope)
        println("📮 AMQP Message (User Created):")
        println("Routing Key: ${createdMessage.routingKey}")
        println(amqpJson.prettyPrint())
        
        // User deleted message
        val deletedMessage = UserDeletedMessage(
            messageId = UUID.randomUUID().toString(),
            userId = userId,
            reason = "User requested account deletion",
            source = "user-service"
        )
        
        val deleteEnvelope = MessageEnvelope(
            headers = headers.plus("event-type" to "user.deleted"),
            properties = properties.copy(correlationId = UUID.randomUUID().toString()),
            body = deletedMessage
        )
        
        val deleteAmqpJson = objectMapper.writeValueAsString(deleteEnvelope)
        println("📮 AMQP Message (User Deleted):")
        println("Routing Key: ${deletedMessage.routingKey}")
        println(deleteAmqpJson.prettyPrint())
    }
    
    private fun demonstrateGrpcProtocol() {
        println("🔗 gRPC Protocol Demo")
        println("-" * 30)
        
        println("📋 Available gRPC Services:")
        println("• UserService.GetUser(GetUserRequest) -> GetUserResponse")
        println("• UserService.CreateUser(CreateUserRequest) -> CreateUserResponse")
        println("• UserService.UpdateUser(UpdateUserRequest) -> UpdateUserResponse")
        println("• UserService.DeleteUser(DeleteUserRequest) -> DeleteUserResponse")
        println("• UserService.ListUsers(ListUsersRequest) -> ListUsersResponse")
        println("• UserService.StreamUserEvents(StreamUserEventsRequest) -> Stream<UserEventMessage>")
        
        println("\n📝 Example gRPC Usage:")
        println("""
        // Create gRPC channel
        val channel = ManagedChannelBuilder.forAddress("localhost", 9090)
            .usePlaintext()
            .build()
        
        // Create service stub
        val userService = UserServiceGrpc.newBlockingStub(channel)
        
        // Create user request
        val request = CreateUserRequest.newBuilder()
            .setUsername("johndoe")
            .setEmail("john@example.com")
            .setFullName("John Doe")
            .build()
        
        // Call service
        val response = userService.createUser(request)
        """.trimIndent())
        
        println("\n🎯 gRPC Features Demonstrated:")
        println("• Strongly-typed service definitions")
        println("• Request/response patterns")
        println("• Streaming support for real-time events")
        println("• Error handling with oneof patterns")
        println("• Pagination support for list operations")
    }
    
    private fun demonstrateCrossProtocolIntegration(userId: Long, username: String, email: String, fullName: String) {
        println("🔄 Cross-Protocol Integration Demo")
        println("-" * 40)
        
        println("Scenario: User creation flows across all protocols")
        println()
        
        // 1. HTTP Request comes in
        val httpRequest = CreateUserRequest(username, email, fullName)
        println("1️⃣ HTTP Request received:")
        println("   ${objectMapper.writeValueAsString(httpRequest)}")
        
        // 2. Business logic creates user
        val createdUser = UserDto(
            id = userId,
            username = username,
            email = email,
            fullName = fullName,
            createdAt = LocalDateTime.now()
        )
        
        // 3. HTTP Response sent back
        val httpResponse = ApiResponse(true, "User created", createdUser)
        println("\n2️⃣ HTTP Response sent:")
        println("   ${objectMapper.writeValueAsString(httpResponse)}")
        
        // 4. WebSocket event broadcasted
        val wsEvent = UserCreatedEvent(
            eventId = UUID.randomUUID().toString(),
            userId = userId,
            username = username,
            email = email,
            fullName = fullName
        )
        println("\n3️⃣ WebSocket event broadcasted:")
        println("   Event Type: USER_CREATED")
        println("   ${objectMapper.writeValueAsString(wsEvent)}")
        
        // 5. AMQP message published
        val amqpMessage = UserCreatedMessage(
            messageId = UUID.randomUUID().toString(),
            userId = userId,
            username = username,
            email = email,
            fullName = fullName
        )
        println("\n4️⃣ AMQP message published:")
        println("   Routing Key: ${amqpMessage.routingKey}")
        println("   ${objectMapper.writeValueAsString(amqpMessage)}")
        
        // 6. gRPC notification (conceptual)
        println("\n5️⃣ gRPC stream notification:")
        println("   Service: UserService.StreamUserEvents")
        println("   Event: USER_EVENT_TYPE_CREATED")
        println("   Payload: User{id=$userId, username='$username'}")
        
        println("\n🎯 Integration Benefits:")
        println("• Consistent data models across all protocols")
        println("• Type safety prevents data corruption")
        println("• Single source of truth for API contracts")
        println("• Easy protocol conversion and mapping")
        println("• Centralized validation and serialization")
    }
    
    private fun String.prettyPrint(): String {
        return try {
            val jsonNode = objectMapper.readTree(this)
            objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode)
        } catch (e: Exception) {
            this
        }
    }
    
    private operator fun String.times(n: Int): String = this.repeat(n)
}

fun main() {
    ProtocolDemo().runDemo()
}