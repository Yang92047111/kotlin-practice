package com.example.spec.dto

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class UserDtoTest {

    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setUp() {
        objectMapper = ObjectMapper()
            .registerModule(KotlinModule.Builder().build())
            .registerModule(JavaTimeModule())
    }

    @Test
    fun `should serialize UserDto to JSON correctly`() {
        // Given
        val userDto = UserDto(
            id = 1L,
            username = "testuser",
            email = "test@example.com",
            fullName = "Test User",
            createdAt = LocalDateTime.of(2024, 1, 1, 12, 0),
            updatedAt = LocalDateTime.of(2024, 1, 2, 12, 0)
        )

        // When
        val json = objectMapper.writeValueAsString(userDto)

        // Then
        assertThat(json).contains("\"id\":1")
        assertThat(json).contains("\"username\":\"testuser\"")
        assertThat(json).contains("\"email\":\"test@example.com\"")
        assertThat(json).contains("\"fullName\":\"Test User\"")
    }

    @Test
    fun `should deserialize JSON to UserDto correctly`() {
        // Given
        val json = """
            {
                "id": 1,
                "username": "testuser",
                "email": "test@example.com",
                "fullName": "Test User",
                "createdAt": "2024-01-01T12:00:00",
                "updatedAt": "2024-01-02T12:00:00"
            }
        """.trimIndent()

        // When
        val userDto = objectMapper.readValue(json, UserDto::class.java)

        // Then
        assertThat(userDto.id).isEqualTo(1L)
        assertThat(userDto.username).isEqualTo("testuser")
        assertThat(userDto.email).isEqualTo("test@example.com")
        assertThat(userDto.fullName).isEqualTo("Test User")
        assertThat(userDto.createdAt).isEqualTo(LocalDateTime.of(2024, 1, 1, 12, 0))
        assertThat(userDto.updatedAt).isEqualTo(LocalDateTime.of(2024, 1, 2, 12, 0))
    }

    @Test
    fun `should serialize CreateUserRequest to JSON correctly`() {
        // Given
        val request = CreateUserRequest(
            username = "newuser",
            email = "new@example.com",
            fullName = "New User"
        )

        // When
        val json = objectMapper.writeValueAsString(request)

        // Then
        assertThat(json).contains("\"username\":\"newuser\"")
        assertThat(json).contains("\"email\":\"new@example.com\"")
        assertThat(json).contains("\"fullName\":\"New User\"")
    }

    @Test
    fun `should deserialize JSON to CreateUserRequest correctly`() {
        // Given
        val json = """
            {
                "username": "newuser",
                "email": "new@example.com",
                "fullName": "New User"
            }
        """.trimIndent()

        // When
        val request = objectMapper.readValue(json, CreateUserRequest::class.java)

        // Then
        assertThat(request.username).isEqualTo("newuser")
        assertThat(request.email).isEqualTo("new@example.com")
        assertThat(request.fullName).isEqualTo("New User")
    }

    @Test
    fun `should serialize UpdateUserRequest with partial fields to JSON correctly`() {
        // Given
        val request = UpdateUserRequest(
            username = "updateduser",
            email = null,
            fullName = "Updated User"
        )

        // When
        val json = objectMapper.writeValueAsString(request)

        // Then
        assertThat(json).contains("\"username\":\"updateduser\"")
        assertThat(json).contains("\"fullName\":\"Updated User\"")
        assertThat(json).contains("\"email\":null")
    }

    @Test
    fun `should serialize ApiResponse with data to JSON correctly`() {
        // Given
        val userDto = UserDto(
            id = 1L,
            username = "testuser",
            email = "test@example.com",
            fullName = "Test User"
        )
        val response = ApiResponse(
            success = true,
            message = "User retrieved successfully",
            data = userDto
        )

        // When
        val json = objectMapper.writeValueAsString(response)

        // Then
        assertThat(json).contains("\"success\":true")
        assertThat(json).contains("\"message\":\"User retrieved successfully\"")
        assertThat(json).contains("\"data\":")
        assertThat(json).contains("\"username\":\"testuser\"")
    }

    @Test
    fun `should serialize ErrorResponse to JSON correctly`() {
        // Given
        val errorResponse = ErrorResponse(
            error = "Validation failed",
            details = listOf("Username is required", "Email format is invalid")
        )

        // When
        val json = objectMapper.writeValueAsString(errorResponse)

        // Then
        assertThat(json).contains("\"error\":\"Validation failed\"")
        assertThat(json).contains("\"details\":")
        assertThat(json).contains("Username is required")
        assertThat(json).contains("Email format is invalid")
    }
}