package com.practice.oracle.controller

import com.practice.oracle.model.User
import com.practice.oracle.service.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.mockito.kotlin.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(UserController::class)
class UserControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var userService: UserService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private lateinit var testUser: User

    @BeforeEach
    fun setUp() {
        testUser = User(id = 1L, name = "John Doe", email = "john@example.com")
    }

    @Test
    fun `create user returns 201 CREATED`() {
        // Given
        val newUser = User(name = "John Doe", email = "john@example.com")
        whenever(userService.create(any())).thenReturn(testUser)

        // When & Then
        mockMvc.perform(
            post("/api/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("John Doe"))
            .andExpect(jsonPath("$.email").value("john@example.com"))

        verify(userService).create(any())
    }

    @Test
    fun `get user by id returns 200 OK`() {
        // Given
        val userId = 1L
        whenever(userService.read(userId)).thenReturn(testUser)

        // When & Then
        mockMvc.perform(get("/api/users/user/{id}", userId))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("John Doe"))
            .andExpect(jsonPath("$.email").value("john@example.com"))

        verify(userService).read(userId)
    }

    @Test
    fun `get user by id throws exception when user not found`() {
        // Given
        val userId = 999L
        whenever(userService.read(userId)).thenThrow(NoSuchElementException("User not found"))

        // When & Then
        mockMvc.perform(get("/api/users/user/{id}", userId))
            .andExpect(status().isInternalServerError)

        verify(userService).read(userId)
    }

    @Test
    fun `update user returns 200 OK`() {
        // Given
        val userId = 1L
        val updatedUser = User(name = "Jane Doe", email = "jane@example.com")
        val returnedUser = User(id = userId, name = "Jane Doe", email = "jane@example.com")
        
        whenever(userService.update(eq(userId), any())).thenReturn(returnedUser)

        // When & Then
        mockMvc.perform(
            put("/api/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(userId))
            .andExpect(jsonPath("$.name").value("Jane Doe"))
            .andExpect(jsonPath("$.email").value("jane@example.com"))

        verify(userService).update(eq(userId), any())
    }

    @Test
    fun `delete user returns 204 NO_CONTENT`() {
        // Given
        val userId = 1L
        doNothing().whenever(userService).delete(userId)

        // When & Then
        mockMvc.perform(delete("/api/users/{id}", userId))
            .andExpect(status().isNoContent)

        verify(userService).delete(userId)
    }

    @Test
    fun `delete user returns 404 NOT_FOUND when user not exists`() {
        // Given
        val userId = 999L
        whenever(userService.delete(userId)).thenThrow(IllegalArgumentException("User not found"))

        // When & Then
        mockMvc.perform(delete("/api/users/{id}", userId))
            .andExpect(status().isNotFound)

        verify(userService).delete(userId)
    }
}