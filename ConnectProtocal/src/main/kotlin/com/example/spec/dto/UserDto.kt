package com.example.spec.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

/**
 * User Data Transfer Object for HTTP REST and WebSocket communication
 */
data class UserDto(
    @JsonProperty("id")
    val id: Long? = null,

    @field:NotBlank(message = "Username cannot be blank")
    @field:Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @JsonProperty("username")
    val username: String,

    @field:NotBlank(message = "Email cannot be blank")
    @field:Email(message = "Email should be valid")
    @JsonProperty("email")
    val email: String,

    @field:NotBlank(message = "Full name cannot be blank")
    @field:Size(max = 100, message = "Full name cannot exceed 100 characters")
    @JsonProperty("fullName")
    val fullName: String,

    @JsonProperty("createdAt")
    val createdAt: LocalDateTime? = null,

    @JsonProperty("updatedAt")
    val updatedAt: LocalDateTime? = null
)

/**
 * User creation request DTO
 */
data class CreateUserRequest(
    @field:NotBlank(message = "Username cannot be blank")
    @field:Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @JsonProperty("username")
    val username: String,

    @field:NotBlank(message = "Email cannot be blank")
    @field:Email(message = "Email should be valid")
    @JsonProperty("email")
    val email: String,

    @field:NotBlank(message = "Full name cannot be blank")
    @field:Size(max = 100, message = "Full name cannot exceed 100 characters")
    @JsonProperty("fullName")
    val fullName: String
)

/**
 * User update request DTO
 */
data class UpdateUserRequest(
    @field:Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @JsonProperty("username")
    val username: String? = null,

    @field:Email(message = "Email should be valid")
    @JsonProperty("email")
    val email: String? = null,

    @field:Size(max = 100, message = "Full name cannot exceed 100 characters")
    @JsonProperty("fullName")
    val fullName: String? = null
)

/**
 * API Response wrapper
 */
data class ApiResponse<T>(
    @JsonProperty("success")
    val success: Boolean,

    @JsonProperty("message")
    val message: String,

    @JsonProperty("data")
    val data: T? = null,

    @JsonProperty("timestamp")
    val timestamp: LocalDateTime = LocalDateTime.now()
)

/**
 * Error response DTO
 */
data class ErrorResponse(
    @JsonProperty("error")
    val error: String,

    @JsonProperty("details")
    val details: List<String>? = null,

    @JsonProperty("timestamp")
    val timestamp: LocalDateTime = LocalDateTime.now()
)