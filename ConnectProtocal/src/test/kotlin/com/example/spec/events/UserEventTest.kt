package com.example.spec.events

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class UserEventTest {

    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setUp() {
        objectMapper = ObjectMapper()
            .registerModule(KotlinModule.Builder().build())
            .registerModule(JavaTimeModule())
    }

    @Test
    fun `should serialize UserCreatedEvent to JSON correctly`() {
        // Given
        val event = UserCreatedEvent(
            eventId = "event-123",
            userId = 1L,
            username = "testuser",
            email = "test@example.com",
            fullName = "Test User"
        )

        // When
        val json = objectMapper.writeValueAsString(event)

        // Then
        assertThat(json).contains("\"eventType\":\"USER_CREATED\"")
        assertThat(json).contains("\"eventId\":\"event-123\"")
        assertThat(json).contains("\"userId\":1")
        assertThat(json).contains("\"username\":\"testuser\"")
        assertThat(json).contains("\"email\":\"test@example.com\"")
        assertThat(json).contains("\"fullName\":\"Test User\"")
    }

    @Test
    fun `should deserialize JSON to UserCreatedEvent correctly`() {
        // Given
        val json = """
            {
                "eventType": "USER_CREATED",
                "eventId": "event-123",
                "userId": 1,
                "username": "testuser",
                "email": "test@example.com",
                "fullName": "Test User",
                "timestamp": "2024-01-01T12:00:00"
            }
        """.trimIndent()

        // When
        val event = objectMapper.readValue(json, UserEvent::class.java) as UserCreatedEvent

        // Then
        assertThat(event.eventId).isEqualTo("event-123")
        assertThat(event.userId).isEqualTo(1L)
        assertThat(event.username).isEqualTo("testuser")
        assertThat(event.email).isEqualTo("test@example.com")
        assertThat(event.fullName).isEqualTo("Test User")
    }

    @Test
    fun `should serialize UserUpdatedEvent to JSON correctly`() {
        // Given
        val updatedFields = mapOf(
            "email" to "newemail@example.com",
            "fullName" to "Updated Name"
        )
        val event = UserUpdatedEvent(
            eventId = "event-456",
            userId = 2L,
            updatedFields = updatedFields
        )

        // When
        val json = objectMapper.writeValueAsString(event)

        // Then
        assertThat(json).contains("\"eventType\":\"USER_UPDATED\"")
        assertThat(json).contains("\"eventId\":\"event-456\"")
        assertThat(json).contains("\"userId\":2")
        assertThat(json).contains("\"updatedFields\":")
        assertThat(json).contains("\"email\":\"newemail@example.com\"")
        assertThat(json).contains("\"fullName\":\"Updated Name\"")
    }

    @Test
    fun `should serialize UserDeletedEvent to JSON correctly`() {
        // Given
        val event = UserDeletedEvent(
            eventId = "event-789",
            userId = 3L,
            reason = "User requested account deletion"
        )

        // When
        val json = objectMapper.writeValueAsString(event)

        // Then
        assertThat(json).contains("\"eventType\":\"USER_DELETED\"")
        assertThat(json).contains("\"eventId\":\"event-789\"")
        assertThat(json).contains("\"userId\":3")
        assertThat(json).contains("\"reason\":\"User requested account deletion\"")
    }

    @Test
    fun `should serialize UserStatusChangedEvent to JSON correctly`() {
        // Given
        val event = UserStatusChangedEvent(
            eventId = "event-101",
            userId = 4L,
            oldStatus = "ACTIVE",
            newStatus = "SUSPENDED"
        )

        // When
        val json = objectMapper.writeValueAsString(event)

        // Then
        assertThat(json).contains("\"eventType\":\"USER_STATUS_CHANGED\"")
        assertThat(json).contains("\"eventId\":\"event-101\"")
        assertThat(json).contains("\"userId\":4")
        assertThat(json).contains("\"oldStatus\":\"ACTIVE\"")
        assertThat(json).contains("\"newStatus\":\"SUSPENDED\"")
    }

    @Test
    fun `should serialize WebSocketMessage to JSON correctly`() {
        // Given
        val event = UserCreatedEvent(
            eventId = "event-123",
            userId = 1L,
            username = "testuser",
            email = "test@example.com",
            fullName = "Test User"
        )
        val message = WebSocketMessage(
            type = "USER_EVENT",
            payload = event
        )

        // When
        val json = objectMapper.writeValueAsString(message)

        // Then
        assertThat(json).contains("\"type\":\"USER_EVENT\"")
        assertThat(json).contains("\"payload\":")
        assertThat(json).contains("\"username\":\"testuser\"")
        assertThat(json).contains("\"timestamp\":")
    }

    @Test
    fun `should deserialize polymorphic UserEvent correctly`() {
        // Given
        val events = listOf(
            """{"eventType":"USER_CREATED","eventId":"1","userId":1,"username":"user1","email":"user1@test.com","fullName":"User One","timestamp":"2024-01-01T12:00:00"}""",
            """{"eventType":"USER_UPDATED","eventId":"2","userId":2,"updatedFields":{"email":"new@test.com"},"timestamp":"2024-01-01T12:00:00"}""",
            """{"eventType":"USER_DELETED","eventId":"3","userId":3,"reason":"test","timestamp":"2024-01-01T12:00:00"}""",
            """{"eventType":"USER_STATUS_CHANGED","eventId":"4","userId":4,"oldStatus":"ACTIVE","newStatus":"INACTIVE","timestamp":"2024-01-01T12:00:00"}"""
        )

        // When & Then
        events.forEach { json ->
            val event = objectMapper.readValue(json, UserEvent::class.java)
            when (event) {
                is UserCreatedEvent -> {
                    assertThat(event.username).isEqualTo("user1")
                    assertThat(event.email).isEqualTo("user1@test.com")
                }
                is UserUpdatedEvent -> {
                    assertThat(event.updatedFields).containsKey("email")
                }
                is UserDeletedEvent -> {
                    assertThat(event.reason).isEqualTo("test")
                }
                is UserStatusChangedEvent -> {
                    assertThat(event.oldStatus).isEqualTo("ACTIVE")
                    assertThat(event.newStatus).isEqualTo("INACTIVE")
                }
            }
        }
    }
}