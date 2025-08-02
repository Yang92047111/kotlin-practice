package com.practice.oracle.controller

import com.practice.oracle.model.User
import com.practice.oracle.service.UserService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.*
import org.mockito.kotlin.*
import org.springframework.http.HttpStatus

class UserControllerTest {

    private lateinit var userService: UserService
    private lateinit var userController: UserController
    private lateinit var testUser: User

    @BeforeEach
    fun setUp() {
        userService = mock()
        userController = UserController(userService)
        testUser = User(id = 1L, name = "John Doe", email = "john@example.com")
    }

    @Test
    fun `create user returns 201 CREATED`() {
        // Given
        val newUser = User(name = "John Doe", email = "john@example.com")
        whenever(userService.create(any())).thenReturn(testUser)

        // When
        val response = userController.createEntity(newUser)

        // Then
        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(testUser, response.body)
        verify(userService).create(newUser)
    }

    @Test
    fun `get user by id returns 200 OK`() {
        // Given
        val userId = 1L
        whenever(userService.read(userId)).thenReturn(testUser)

        // When
        val response = userController.getEntity(userId)

        // Then
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(testUser, response.body)
        verify(userService).read(userId)
    }

    @Test
    fun `get user by id throws exception when user not found`() {
        // Given
        val userId = 999L
        whenever(userService.read(userId)).thenThrow(NoSuchElementException("User not found"))

        // When & Then
        assertThrows(NoSuchElementException::class.java) {
            userController.getEntity(userId)
        }
        verify(userService).read(userId)
    }

    @Test
    fun `update user returns 200 OK`() {
        // Given
        val userId = 1L
        val updatedUser = User(name = "Jane Doe", email = "jane@example.com")
        val returnedUser = User(id = userId, name = "Jane Doe", email = "jane@example.com")
        
        whenever(userService.update(eq(userId), any())).thenReturn(returnedUser)

        // When
        val response = userController.updateEntity(userId, updatedUser)

        // Then
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(returnedUser, response.body)
        verify(userService).update(eq(userId), any())
    }

    @Test
    fun `delete user returns 204 NO_CONTENT`() {
        // Given
        val userId = 1L
        doNothing().whenever(userService).delete(userId)

        // When
        val response = userController.deleteEntity(userId)

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        assertNull(response.body)
        verify(userService).delete(userId)
    }

    @Test
    fun `delete user returns 404 NOT_FOUND when user not exists`() {
        // Given
        val userId = 999L
        whenever(userService.delete(userId)).thenThrow(IllegalArgumentException("User not found"))

        // When
        val response = userController.deleteEntity(userId)

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertNull(response.body)
        verify(userService).delete(userId)
    }
}