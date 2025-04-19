package com.practice.repository

import com.practice.model.Note

interface NoteRepository {
    fun create(note: Note): Note
    fun findById(id: Long): Note?
    fun findAll(): List<Note>
    fun update(id: Long, note: Note): Note?
    fun delete(id: Long): Boolean
}