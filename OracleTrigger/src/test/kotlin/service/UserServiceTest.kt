package com.practice.oracle.service

import com.practice.oracle.model.User
import com.practice.oracle.repository.UserRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate

class UserServiceTest {

    private lateinit var userRepository: UserRepository
    private lateinit var restTemplate: RestTemplate
    private lateinit var userService: UserService

    @BeforeEach
    fun setUp() {
        userRepository = mock()
        restTemplate = mock()
        userService = UserService(userRepository, restTemplate)
    }

    @Test
    fun `create user successfully`() {
        // Given
        val user = User(name = "John Doe", email = "john@example.com")
        val successResponse = ResponseEntity("Success", HttpStatus.OK)
        
        whenever(restTemplate.postForEntity(any<String>(), any(), eq(String::class.java)))
            .thenReturn(successResponse)

        // When
        val result = userService.create(user)

        // Then
        assertEquals(user, result)
        verify(userRepository).insert(user)
        verify(restTemplate).postForEntity("https://external.api/service", user, String::class.java)
    }

    @Test
    fun `create user fails when external API returns error status`() {
        // Given
        val user = User(name = "John Doe", email = "john@example.com")
        val errorResponse = ResponseEntity("Error", HttpStatus.INTERNAL_SERVER_ERROR)
        
        whenever(restTemplate.postForEntity(any<String>(), any(), eq(String::class.java)))
            .thenReturn(errorResponse)

        // When & Then
        assertThrows<RuntimeException> {
            userService.create(user)
        }
        
        verify(userRepository).insert(user)
    }

    @Test
    fun `create user fails when external API throws exception`() {
        // Given
        val user = User(name = "John Doe", email = "john@example.com")
        
        whenever(restTemplate.postForEntity(any<String>(), any(), eq(String::class.java)))
            .thenThrow(RestClientException("Connection failed"))

        // When & Then
        assertThrows<RuntimeException> {
            userService.create(user)
        }
        
        verify(userRepository).insert(user)
    }

    @Test
    fun `read user successfully`() {
        // Given
        val userId = 1L
        val user = User(id = userId, name = "John Doe", email = "john@example.com")
        
        whenever(userRepository.findById(userId)).thenReturn(user)

        // When
        val result = userService.read(userId)

        // Then
        assertEquals(user, result)
        verify(userRepository).findById(userId)
    }

    @Test
    fun `read user throws exception when user not found`() {
        // Given
        val userId = 1L
        
        whenever(userRepository.findById(userId)).thenReturn(null)

        // When & Then
        assertThrows<NoSuchElementException> {
            userService.read(userId)
        }
    }

    @Test
    fun `update user successfully`() {
        // Given
        val userId = 1L
        val existingUser = User(id = userId, name = "John Doe", email = "john@example.com")
        val updatedUser = User(name = "Jane Doe", email = "jane@example.com")
        val successResponse = ResponseEntity("Success", HttpStatus.OK)
        
        whenever(userRepository.findById(userId)).thenReturn(existingUser)
        whenever(restTemplate.postForEntity(any<String>(), any(), eq(String::class.java)))
            .thenReturn(successResponse)

        // When
        val result = userService.update(userId, updatedUser)

        // Then
        assertEquals(userId, result.id)
        assertEquals(updatedUser.name, result.name)
        assertEquals(updatedUser.email, result.email)
        verify(userRepository).findById(userId)
        verify(userRepository).insert(updatedUser.copy(id = userId))
    }

    @Test
    fun `update user fails when user not found`() {
        // Given
        val userId = 1L
        val updatedUser = User(name = "Jane Doe", email = "jane@example.com")
        
        whenever(userRepository.findById(userId)).thenReturn(null)

        // When & Then
        assertThrows<IllegalArgumentException> {
            userService.update(userId, updatedUser)
        }
    }

    @Test
    fun `delete user successfully`() {
        // Given
        val userId = 1L
        val user = User(id = userId, name = "John Doe", email = "john@example.com")
        val successResponse = ResponseEntity("Success", HttpStatus.OK)
        
        whenever(userRepository.findById(userId)).thenReturn(user)
        whenever(restTemplate.postForEntity(any<String>(), any(), eq(String::class.java)))
            .thenReturn(successResponse)

        // When
        userService.delete(userId)

        // Then
        verify(userRepository).findById(userId)
        verify(userRepository).delete(userId)
        verify(restTemplate).postForEntity("https://external.api/deleteNotify", user, String::class.java)
    }

    @Test
    fun `delete user fails when user not found`() {
        // Given
        val userId = 1L
        
        whenever(userRepository.findById(userId)).thenReturn(null)

        // When & Then
        assertThrows<IllegalArgumentException> {
            userService.delete(userId)
        }
    }
}