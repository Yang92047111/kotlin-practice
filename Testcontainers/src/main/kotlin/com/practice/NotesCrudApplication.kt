package com.practice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NotesCrudApplication

fun main(args: Array<String>) {
    runApplication<NotesCrudApplication>(*args)
}