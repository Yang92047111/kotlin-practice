package com.practice.controller

import com.practice.model.Note
import com.practice.service.NoteService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/notes")
class NotesController(private val noteService: NoteService) {

    @PostMapping
    fun createNote(@RequestBody note: Note): ResponseEntity<Note> {
        val createdNote = noteService.createNote(note)
        return ResponseEntity(createdNote, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getNoteById(@PathVariable id: Long): ResponseEntity<Note> {
        val note = noteService.getNoteById(id)
        return if (note != null) {
            ResponseEntity(note, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping
    fun getAllNotes(): ResponseEntity<List<Note>> {
        val notes = noteService.getAllNotes()
        return ResponseEntity(notes, HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun updateNote(@PathVariable id: Long, @RequestBody note: Note): ResponseEntity<Note> {
        val updatedNote = noteService.updateNote(id, note)
        return if (updatedNote != null) {
            ResponseEntity(updatedNote, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteNote(@PathVariable id: Long): ResponseEntity<Void> {
        return if (noteService.deleteNoteById(id)) {
            ResponseEntity(HttpStatus.NO_CONTENT)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}