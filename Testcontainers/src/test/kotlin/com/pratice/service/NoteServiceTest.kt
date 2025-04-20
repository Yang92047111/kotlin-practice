package com.practice.service

import com.practice.model.Note
import com.practice.repository.JdbiNoteRepository
import com.practice.repository.NoteRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class NoteServiceTest {

    private lateinit var noteRepository: NoteRepository
    private lateinit var noteService: NoteService

    @BeforeEach
    fun setUp() {
        noteRepository = mock()
        noteService = NoteService(noteRepository)
    }

    @Test
    fun `should create a note`() {
        val note = Note(id = null, title = "Test Title", content = "Test Content")
        val createdNote = note.copy(id = 1L)

        whenever(noteRepository.create(note)).thenReturn(createdNote)

        val result = noteService.createNote(note)

        assertNotNull(result.id)
        assertEquals(note.title, result.title)
        assertEquals(note.content, result.content)
        verify(noteRepository).create(note)
    }

    @Test
    fun `should get a note by id`() {
        val note = Note(id = 1L, title = "Test Title", content = "Test Content")

        whenever(noteRepository.findById(1L)).thenReturn(note)

        val result = noteService.getNoteById(1L)

        assertNotNull(result)
        assertEquals(note.id, result?.id)
        assertEquals(note.title, result?.title)
        assertEquals(note.content, result?.content)
        verify(noteRepository).findById(1L)
    }

    @Test
    fun `should return null when getting a non-existent note by id`() {
        whenever(noteRepository.findById(999L)).thenReturn(null)

        val result = noteService.getNoteById(999L)

        assertNull(result)
        verify(noteRepository).findById(999L)
    }

    @Test
    fun `should get all notes`() {
        val notes = listOf(
            Note(id = 1L, title = "Title 1", content = "Content 1"),
            Note(id = 2L, title = "Title 2", content = "Content 2")
        )

        whenever(noteRepository.findAll()).thenReturn(notes)

        val result = noteService.getAllNotes()

        assertEquals(2, result.size)
        assertEquals(notes, result)
        verify(noteRepository).findAll()
    }

    @Test
    fun `should update a note`() {
        val note = Note(id = null, title = "Updated Title", content = "Updated Content")
        val updatedNote = note.copy(id = 1L)

        whenever(noteRepository.update(1L, note)).thenReturn(updatedNote)

        val result = noteService.updateNote(1L, note)

        assertNotNull(result)
        assertEquals(updatedNote.id, result?.id)
        assertEquals(updatedNote.title, result?.title)
        assertEquals(updatedNote.content, result?.content)
        verify(noteRepository).update(1L, note)
    }

    @Test
    fun `should return null when updating a non-existent note`() {
        val note = Note(id = null, title = "Updated Title", content = "Updated Content")

        whenever(noteRepository.update(999L, note)).thenReturn(null)

        val result = noteService.updateNote(999L, note)

        assertNull(result)
        verify(noteRepository).update(999L, note)
    }

    @Test
    fun `should delete a note by id`() {
        whenever(noteRepository.delete(1L)).thenReturn(true)

        val result = noteService.deleteNoteById(1L)

        assertTrue(result)
        verify(noteRepository).delete(1L)
    }

    @Test
    fun `should return false when deleting a non-existent note`() {
        whenever(noteRepository.delete(999L)).thenReturn(false)

        val result = noteService.deleteNoteById(999L)

        assertFalse(result)
        verify(noteRepository).delete(999L)
    }
}