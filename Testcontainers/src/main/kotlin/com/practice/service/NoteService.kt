package com.practice.service

import com.practice.model.Note
import com.practice.repository.JdbiNoteRepository
import com.practice.repository.NoteRepository
import org.springframework.stereotype.Service

@Service
open class NoteService(private val noteRepository: NoteRepository) {

    fun createNote(note: Note): Note {
        return noteRepository.create(note)
    }

    fun getNoteById(id: Long): Note? {
        return noteRepository.findById(id)
    }

    fun getAllNotes(): List<Note> {
        return noteRepository.findAll()
    }

    fun updateNote(id: Long, note: Note): Note? {
        return noteRepository.update(id, note)
    }

    fun deleteNoteById(id: Long): Boolean {
        return noteRepository.delete(id)
    }
}