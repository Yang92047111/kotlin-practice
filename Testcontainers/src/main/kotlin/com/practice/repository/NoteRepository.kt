package com.practice.testcontainer.repository

import com.practice.testcontainer.model.Note

interface NoteRepository {
    fun create(note: Note): Note
    fun findById(id: Long): Note?
    fun findAll(): List<Note>
    fun update(id: Long, note: Note): Note?
    fun delete(id: Long): Boolean
}