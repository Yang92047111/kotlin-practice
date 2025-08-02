package com.practice.oracle.repository

import com.practice.oracle.model.User
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.mockito.kotlin.*
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import javax.sql.DataSource

class UserRepositoryTest {

    private lateinit var dataSource: DataSource
    private lateinit var jdbcTemplate: JdbcTemplate
    private lateinit var userRepository: UserRepository

    @BeforeEach
    fun setUp() {
        dataSource = mock()
        jdbcTemplate = mock()
        userRepository = UserRepository(dataSource)
        
        // Use reflection to inject the mocked jdbcTemplate
        val field = UserRepository::class.java.getDeclaredField("jdbcTemplate")
        field.isAccessible = true
        field.set(userRepository, jdbcTemplate)
    }

    @Test
    fun `findAll returns list of users`() {
        // Given
        val users = listOf(
            User(id = 1L, name = "John Doe", email = "john@example.com"),
            User(id = 2L, name = "Jane Doe", email = "jane@example.com")
        )
        
        whenever(jdbcTemplate.query(any<String>(), any<RowMapper<User>>())).thenReturn(users)

        // When
        val result = userRepository.findAll()

        // Then
        assertEquals(2, result.size)
        assertEquals(users, result)
        verify(jdbcTemplate).query(eq("SELECT * FROM your_table"), any<RowMapper<User>>())
    }

    @Test
    fun `findById returns user when found`() {
        // Given
        val userId = 1L
        val user = User(id = userId, name = "John Doe", email = "john@example.com")
        
        whenever(jdbcTemplate.queryForObject(any<String>(), any<RowMapper<User>>(), eq(userId)))
            .thenReturn(user)

        // When
        val result = userRepository.findById(userId)

        // Then
        assertEquals(user, result)
        verify(jdbcTemplate).queryForObject(
            eq("SELECT * FROM your_table WHERE id = ?"), 
            any<RowMapper<User>>(), 
            eq(userId)
        )
    }

    @Test
    fun `insert user executes update statement`() {
        // Given
        val user = User(name = "John Doe", email = "john@example.com")
        
        whenever(jdbcTemplate.update(any<String>(), any(), any())).thenReturn(1)

        // When
        userRepository.insert(user)

        // Then
        verify(jdbcTemplate).update(
            eq("INSERT INTO your_table (name, email) VALUES (?, ?)"),
            eq(user.name),
            eq(user.email)
        )
    }

    @Test
    fun `update user executes update statement`() {
        // Given
        val user = User(id = 1L, name = "John Doe", email = "john@example.com")
        
        whenever(jdbcTemplate.update(any<String>(), any(), any(), any())).thenReturn(1)

        // When
        userRepository.update(user)

        // Then
        verify(jdbcTemplate).update(
            eq("UPDATE your_table SET name = ?, email = ? WHERE id = ?"),
            eq(user.name),
            eq(user.email),
            eq(user.id)
        )
    }

    @Test
    fun `delete user when user exists`() {
        // Given
        val userId = 1L
        val user = User(id = userId, name = "John Doe", email = "john@example.com")
        
        whenever(jdbcTemplate.queryForObject(any<String>(), any<RowMapper<User>>(), eq(userId)))
            .thenReturn(user)
        whenever(jdbcTemplate.update(any<String>(), eq(userId))).thenReturn(1)

        // When
        userRepository.delete(userId)

        // Then
        verify(jdbcTemplate).queryForObject(
            eq("SELECT * FROM your_table WHERE id = ?"), 
            any<RowMapper<User>>(), 
            eq(userId)
        )
        verify(jdbcTemplate).update(eq("DELETE FROM your_table WHERE id = ?"), eq(userId))
    }

    @Test
    fun `delete user does nothing when user not found`() {
        // Given
        val userId = 999L
        
        whenever(jdbcTemplate.queryForObject(any<String>(), any<RowMapper<User>>(), eq(userId)))
            .thenReturn(null)

        // When
        userRepository.delete(userId)

        // Then
        verify(jdbcTemplate).queryForObject(
            eq("SELECT * FROM your_table WHERE id = ?"), 
            any<RowMapper<User>>(), 
            eq(userId)
        )
        verify(jdbcTemplate, never()).update(eq("DELETE FROM your_table WHERE id = ?"), eq(userId))
    }
}