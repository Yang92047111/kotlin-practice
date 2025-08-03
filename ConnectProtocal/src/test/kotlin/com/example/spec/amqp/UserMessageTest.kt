package com.example.spec.amqp

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class UserMessageTest {

    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setUp() {
        objectMapper = ObjectMapper()
            .registerModule(KotlinModule.Builder().build())
            .registerModule(JavaTimeModule())
    }

    @Test
    fun `should serialize UserCreatedMessage to JSON correctly`() {
        // Given
        val message = UserCreatedMessage(
            messageId = "msg-123",
            userId = 1L,
            username = "testuser",
            email = "test@example.com",
            fullName = "Test User",
            source = "user-service"
        )

        // When
        val json = objectMapper.writeValueAsString(message)

        // Then
        assertThat(json).contains("\"messageId\":\"msg-123\"")
        assertThat(json).contains("\"userId\":1")
        assertThat(json).contains("\"username\":\"testuser\"")
        assertThat(json).contains("\"email\":\"test@example.com\"")
        assertThat(json).contains("\"fullName\":\"Test User\"")
        assertThat(json).contains("\"source\":\"user-service\"")
        assertThat(json).contains("\"routingKey\":\"user.created\"")
    }

    @Test
    fun `should deserialize JSON to UserCreatedMessage correctly`() {
        // Given
        val json = """
            {
                "messageId": "msg-123",
                "userId": 1,
                "username": "testuser",
                "email": "test@example.com",
                "fullName": "Test User",
                "source": "user-service",
                "routingKey": "user.created",
                "timestamp": "2024-01-01T12:00:00"
            }
        """.trimIndent()

        // When
        val message = objectMapper.readValue(json, UserCreatedMessage::class.java)

        // Then
        assertThat(message.messageId).isEqualTo("msg-123")
        assertThat(message.userId).isEqualTo(1L)
        assertThat(message.username).isEqualTo("testuser")
        assertThat(message.email).isEqualTo("test@example.com")
        assertThat(message.fullName).isEqualTo("Test User")
        assertThat(message.source).isEqualTo("user-service")
        assertThat(message.routingKey).isEqualTo("user.created")
    }

    @Test
    fun `should serialize UserUpdatedMessage to JSON correctly`() {
        // Given
        val updatedFields = mapOf(
            "email" to "newemail@example.com",
            "fullName" to "Updated Name"
        )
        val message = UserUpdatedMessage(
            messageId = "msg-456",
            userId = 2L,
            updatedFields = updatedFields
        )

        // When
        val json = objectMapper.writeValueAsString(message)

        // Then
        assertThat(json).contains("\"messageId\":\"msg-456\"")
        assertThat(json).contains("\"userId\":2")
        assertThat(json).contains("\"updatedFields\":")
        assertThat(json).contains("\"email\":\"newemail@example.com\"")
        assertThat(json).contains("\"routingKey\":\"user.updated\"")
    }

    @Test
    fun `should serialize UserDeletedMessage to JSON correctly`() {
        // Given
        val message = UserDeletedMessage(
            messageId = "msg-789",
            userId = 3L,
            reason = "User requested deletion"
        )

        // When
        val json = objectMapper.writeValueAsString(message)

        // Then
        assertThat(json).contains("\"messageId\":\"msg-789\"")
        assertThat(json).contains("\"userId\":3")
        assertThat(json).contains("\"reason\":\"User requested deletion\"")
        assertThat(json).contains("\"routingKey\":\"user.deleted\"")
    }

    @Test
    fun `should serialize UserStatusChangedMessage to JSON correctly`() {
        // Given
        val message = UserStatusChangedMessage(
            messageId = "msg-101",
            userId = 4L,
            oldStatus = "ACTIVE",
            newStatus = "SUSPENDED"
        )

        // When
        val json = objectMapper.writeValueAsString(message)

        // Then
        assertThat(json).contains("\"messageId\":\"msg-101\"")
        assertThat(json).contains("\"userId\":4")
        assertThat(json).contains("\"oldStatus\":\"ACTIVE\"")
        assertThat(json).contains("\"newStatus\":\"SUSPENDED\"")
        assertThat(json).contains("\"routingKey\":\"user.status.changed\"")
    }

    @Test
    fun `should serialize MessageEnvelope to JSON correctly`() {
        // Given
        val message = UserCreatedMessage(
            messageId = "msg-123",
            userId = 1L,
            username = "testuser",
            email = "test@example.com",
            fullName = "Test User"
        )
        val headers = mapOf(
            "source" to "user-service",
            "version" to "1.0"
        )
        val properties = MessageProperties(
            contentType = "application/json",
            deliveryMode = 2,
            correlationId = "corr-123"
        )
        val envelope = MessageEnvelope(
            headers = headers,
            properties = properties,
            body = message
        )

        // When
        val json = objectMapper.writeValueAsString(envelope)

        // Then
        assertThat(json).contains("\"headers\":")
        assertThat(json).contains("\"source\":\"user-service\"")
        assertThat(json).contains("\"properties\":")
        assertThat(json).contains("\"contentType\":\"application/json\"")
        assertThat(json).contains("\"deliveryMode\":2")
        assertThat(json).contains("\"correlationId\":\"corr-123\"")
        assertThat(json).contains("\"body\":")
        assertThat(json).contains("\"username\":\"testuser\"")
    }

    @Test
    fun `should serialize MessageProperties to JSON correctly`() {
        // Given
        val properties = MessageProperties(
            contentType = "application/json",
            deliveryMode = 2,
            priority = 1,
            correlationId = "corr-456",
            replyTo = "reply-queue",
            expiration = "60000"
        )

        // When
        val json = objectMapper.writeValueAsString(properties)

        // Then
        assertThat(json).contains("\"contentType\":\"application/json\"")
        assertThat(json).contains("\"deliveryMode\":2")
        assertThat(json).contains("\"priority\":1")
        assertThat(json).contains("\"correlationId\":\"corr-456\"")
        assertThat(json).contains("\"replyTo\":\"reply-queue\"")
        assertThat(json).contains("\"expiration\":\"60000\"")
        assertThat(json).contains("\"timestamp\":")
    }
}