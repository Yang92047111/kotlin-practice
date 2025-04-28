package com.practice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.SpringApplication

@SpringBootApplication
class NotesCrudApplication

fun main(args: Array<String>) {
    SpringApplication.run(NotesCrudApplication::class.java, *args)
}