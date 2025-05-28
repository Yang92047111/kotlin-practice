package com.practice.oracle.controller

import com.practice.oracle.model.User
import com.practice.oracle.service.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.mockito.BDDMockito.*
import org.mockito.Mockito
import org.mockito.plugins.MockitoPlugins
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.MvcResult

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var userService: UserService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `should return 400 when creating a user with invalid data`() {
        val invalidUser = User(id = 1, name = "", email = "invalid-email")

        Mockito.`when`(userService.create(any(User::class.java))).thenThrow(IllegalArgumentException::class.java)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidUser))
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    // @Test
    // fun `should return 404 when user not found by id`() {
    //     Mockito.`when`(userService.read(99)).thenThrow(IllegalArgumentException::class.java)

    //     mockMvc.perform(MockMvcRequestBuilders.get("/api/user/99"))
    //         .andExpect(MockMvcResultMatchers.status().isNotFound)
    // }

    // @Test
    // fun `should return 400 when updating a user with invalid data`() {
    //     val invalidUser = User(id = 1, name = "", email = "invalid-email")

    //     Mockito.`when`(userService.update(1, any(User::class.java))).thenThrow(IllegalArgumentException::class.java)

    //     mockMvc.perform(
    //         MockMvcRequestBuilders.put("/api/user/1")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(objectMapper.writeValueAsString(invalidUser))
    //     ).andExpect(MockMvcResultMatchers.status().isBadRequest)
    // }

    // @Test
    // fun `should return 404 when updating a non-existent user`() {
    //     val user = User(id = 99, name = "NonExistent", email = "nonexistent@example.com")

    //     Mockito.`when`(userService.update(99, any(User::class.java))).thenThrow(IllegalArgumentException::class.java)

    //     mockMvc.perform(
    //         MockMvcRequestBuilders.put("/api/user/99")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(objectMapper.writeValueAsString(user))
    //     ).andExpect(MockMvcResultMatchers.status().isNotFound)
    // }

    // @Test
    // fun `should create a new user`() {
    //     val user = User(id = 1, name = "John", email = "john@example.com")

    //     Mockito.`when`(userService.create(any(User::class.java))).thenReturn(user)

    //     mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
    //         .contentType(MediaType.APPLICATION_JSON)
    //         .content(objectMapper.writeValueAsString(user))
    //         )
    //         .andDo(MockMvcResultHandlers.print())
    //         .andExpect(MockMvcResultMatchers.status().isCreated)
    //         .andReturn()
    // }

    // @Test
    // fun `should return a user by id`() {
    //     val user = User(id = 1, name = "Alice", email = "alice@example.com")

    //     Mockito.`when`(userService.read(1)).thenReturn(user)

    //     mockMvc.perform(MockMvcRequestBuilders.get("/api/user/1"))
    //     .andDo(MockMvcResultHandlers.print())
    //     .andExpect(MockMvcResultMatchers.status().isOk)
    //     .andReturn()
    // }

    // @Test
    // fun `should update a user`() {
    //     val updated = User(id = 1, name = "Updated", email = "updated@example.com")

    //     Mockito.`when`(userService.update(1, any(User::class.java))).thenReturn(updated)

    //     mockMvc.perform(
    //         MockMvcRequestBuilders.put("/api/user/1")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(objectMapper.writeValueAsString(updated))
    //     ).andExpect(MockMvcResultMatchers.status().isOk)
    // }

    // private fun performPostRequest(url: String, body: Any) = 
    //     mockMvc.perform(
    //         MockMvcRequestBuilders.post(url)
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(objectMapper.writeValueAsString(body))
    //     )

    // @Test
    // fun `should delete a user successfully`() {
    //     Mockito.`when`(userService.delete(1)).thenReturn(Unit)

    //     mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/1"))
    //         .andExpect(MockMvcResultMatchers.status().isNoContent)
    // }

    // @Test
    // fun `should return 404 when delete fails`() {
    //     Mockito.`when`(userService.delete(99)).thenThrow(IllegalArgumentException::class.java)

    //     mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/99"))
    //         .andExpect(MockMvcResultMatchers.status().isNotFound)
    // }
}
