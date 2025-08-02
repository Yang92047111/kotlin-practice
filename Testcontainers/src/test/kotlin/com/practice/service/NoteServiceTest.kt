package com.practice.testcontainer.service

import com.practice.testcontainer.model.Note
import com.practice.testcontainer.repository.NoteRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.mockito.kotlin.*

class NoteServiceTest {

    private lateinit var noteRepository: NoteRepository
    private lateinit var noteService: NoteService

    @BeforeEach
    fun setUp() {
        noteRepository = mock()
        noteService = NoteService(noteRepository)
    }

    @Test
    fun `createNote should call repository create`() {
        // Given
        val note = Note(title = "Test Note", content = "Test Content")
        val createdNote = Note(id = 1L, title = "Test Note", content = "Test Content")
        
        whenever(noteRepository.create(note)).thenReturn(createdNote)

        // When
        val result = noteService.createNote(note)

        // Then
        assertEquals(createdNote, result)
        verify(noteRepository).create(note)
    }

    @Test
    fun `getNoteById should return note when found`() {
        // Given
        val noteId = 1L
        val note = Note(id = noteId, title = "Test Note", content = "Test Content")
        
        whenever(noteRepository.findById(noteId)).thenReturn(note)

        // When
        val result = noteService.getNoteById(noteId)

        // Then
        assertEquals(note, result)
        verify(noteRepository).findById(noteId)
    }

    @Test
    fun `getNoteById should return null when not found`() {
        // Given
        val noteId = 999L
        
        whenever(noteRepository.findById(noteId)).thenReturn(null)

        // When
        val result = noteService.getNoteById(noteId)

        // Then
        assertNull(result)
        verify(noteRepository).findById(noteId)
    }

    @Test
    fun `getAllNotes should return all notes`() {
        // Given
        val notes = listOf(
            Note(id = 1L, title = "Note 1", content = "Content 1"),
            Note(id = 2L, title = "Note 2", content = "Content 2")
        )
        
        whenever(noteRepository.findAll()).thenReturn(notes)

        // When
        val result = noteService.getAllNotes()

        // Then
        assertEquals(notes, result)
        verify(noteRepository).findAll()
    }

    @Test
    fun `updateNote should return updated note when successful`() {
        // Given
        val noteId = 1L
        val note = Note(title = "Updated Note", content = "Updated Content")
        val updatedNote = Note(id = noteId, title = "Updated Note", content = "Updated Content")
        
        whenever(noteRepository.update(noteId, note)).thenReturn(updatedNote)

        // When
        val result = noteService.updateNote(noteId, note)

        // Then
        assertEquals(updatedNote, result)
        verify(noteRepository).update(noteId, note)
    }

    @Test
    fun `updateNote should return null when note not found`() {
        // Given
        val noteId = 999L
        val note = Note(title = "Updated Note", content = "Updated Content")
        
        whenever(noteRepository.update(noteId, note)).thenReturn(null)

        // When
        val result = noteService.updateNote(noteId, note)

        // Then
        assertNull(result)
        verify(noteRepository).update(noteId, note)
    }

    @Test
    fun `deleteNoteById should return true when successful`() {
        // Given
        val noteId = 1L
        
        whenever(noteRepository.delete(noteId)).thenReturn(true)

        // When
        val result = noteService.deleteNoteById(noteId)

        // Then
        assertTrue(result)
        verify(noteRepository).delete(noteId)
    }

    @Test
    fun `deleteNoteById should return false when note not found`() {
        // Given
        val noteId = 999L
        
        whenever(noteRepository.delete(noteId)).thenReturn(false)

        // When
        val result = noteService.deleteNoteById(noteId)

        // Then
        assertFalse(result)
        verify(noteRepository).delete(noteId)
    }
}