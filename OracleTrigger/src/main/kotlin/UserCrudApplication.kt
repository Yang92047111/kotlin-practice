package com.practice.oracle

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.SpringApplication

@SpringBootApplication
class UserCrudApplication

fun main(args: Array<String>) {
    SpringApplication.run(UserCrudApplication::class.java, *args)
}