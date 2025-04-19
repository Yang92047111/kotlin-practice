package com.practice.service

import com.practice.model.Note
import com.practice.repository.JdbiNoteRepository
import org.springframework.stereotype.Service

@Service
class NoteService(private val noteRepository: JdbiNoteRepository) {

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