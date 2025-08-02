package com.practice.testcontainer.controller

import com.practice.testcontainer.model.Note
import com.practice.testcontainer.service.NoteService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.*
import org.mockito.kotlin.*
import org.springframework.http.HttpStatus

class NoteControllerTest {

    private lateinit var noteService: NoteService
    private lateinit var noteController: NoteController
    private lateinit var testNote: Note

    @BeforeEach
    fun setUp() {
        noteService = mock()
        noteController = NoteController(noteService)
        testNote = Note(id = 1L, title = "Test Note", content = "Test Content")
    }

    @Test
    fun `createNote returns 201 CREATED`() {
        // Given
        val newNote = Note(title = "Test Note", content = "Test Content")
        whenever(noteService.createNote(any())).thenReturn(testNote)

        // When
        val response = noteController.createNote(newNote)

        // Then
        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(testNote, response.body)
        verify(noteService).createNote(newNote)
    }

    @Test
    fun `getNoteById returns 200 OK when note exists`() {
        // Given
        val noteId = 1L
        whenever(noteService.getNoteById(noteId)).thenReturn(testNote)

        // When
        val response = noteController.getNoteById(noteId)

        // Then
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(testNote, response.body)
        verify(noteService).getNoteById(noteId)
    }

    @Test
    fun `getNoteById returns 404 NOT_FOUND when note does not exist`() {
        // Given
        val noteId = 999L
        whenever(noteService.getNoteById(noteId)).thenReturn(null)

        // When
        val response = noteController.getNoteById(noteId)

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertNull(response.body)
        verify(noteService).getNoteById(noteId)
    }

    @Test
    fun `getAllNotes returns 200 OK with list of notes`() {
        // Given
        val notes = listOf(
            testNote,
            Note(id = 2L, title = "Note 2", content = "Content 2")
        )
        whenever(noteService.getAllNotes()).thenReturn(notes)

        // When
        val response = noteController.getAllNotes()

        // Then
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(notes, response.body)
        assertEquals(2, response.body?.size)
        verify(noteService).getAllNotes()
    }

    @Test
    fun `updateNote returns 200 OK when note is updated`() {
        // Given
        val noteId = 1L
        val updatedNote = Note(title = "Updated Note", content = "Updated Content")
        val returnedNote = Note(id = noteId, title = "Updated Note", content = "Updated Content")
        
        whenever(noteService.updateNote(eq(noteId), any())).thenReturn(returnedNote)

        // When
        val response = noteController.updateNote(noteId, updatedNote)

        // Then
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(returnedNote, response.body)
        verify(noteService).updateNote(eq(noteId), any())
    }

    @Test
    fun `updateNote returns 404 NOT_FOUND when note does not exist`() {
        // Given
        val noteId = 999L
        val updatedNote = Note(title = "Updated Note", content = "Updated Content")
        
        whenever(noteService.updateNote(eq(noteId), any())).thenReturn(null)

        // When
        val response = noteController.updateNote(noteId, updatedNote)

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertNull(response.body)
        verify(noteService).updateNote(eq(noteId), any())
    }

    @Test
    fun `deleteNote returns 204 NO_CONTENT when note is deleted`() {
        // Given
        val noteId = 1L
        whenever(noteService.deleteNoteById(noteId)).thenReturn(true)

        // When
        val response = noteController.deleteNote(noteId)

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        assertNull(response.body)
        verify(noteService).deleteNoteById(noteId)
    }

    @Test
    fun `deleteNote returns 404 NOT_FOUND when note does not exist`() {
        // Given
        val noteId = 999L
        whenever(noteService.deleteNoteById(noteId)).thenReturn(false)

        // When
        val response = noteController.deleteNote(noteId)

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertNull(response.body)
        verify(noteService).deleteNoteById(noteId)
    }
}