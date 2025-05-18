package com.practice.oracle.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.practice.oracle.service.UserService
import com.practice.oracle.model.User

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @PostMapping("/")
    fun createEntity(@RequestBody user: User): ResponseEntity<User> {
        val createdUser = userService.create(user)
        return ResponseEntity(createdUser, HttpStatus.CREATED)
    }

    @GetMapping("/user/{id}")
    fun getEntity(@PathVariable id: Long): ResponseEntity<User> {
        val user = userService.read(id)
        return ResponseEntity(user, HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun updateEntity(@PathVariable id: Long, @RequestBody user: User): ResponseEntity<User> {
        val updatedEntity = userService.update(id, user)
        return ResponseEntity(updatedEntity, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteEntity(@PathVariable id: Long): ResponseEntity<Void> {
        return try {
            userService.delete(id)
            ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (ex: IllegalArgumentException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}