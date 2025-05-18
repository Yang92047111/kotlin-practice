package com.practice.testcontainer.service

import com.practice.testcontainer.model.Note
import com.practice.testcontainer.repository.JdbiNoteRepository
import com.practice.testcontainer.repository.NoteRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean

class NoteServiceTest {
    @Autowired
    lateinit var noteService: NoteService

    @MockBean
    lateinit var noteRepository: NoteRepository

    @Test
    fun `should create a note`() {
        val note = Note(id = null, title = "Test Title", content = "Test Content")
        val createdNote = note.copy(id = 1L)

        Mockito.`when`(noteRepository.create(note)).thenReturn(createdNote)

        val result = noteService.createNote(note)

        assertNotNull(result.id)
        assertEquals(note.title, result.title)
        assertEquals(note.content, result.content)
        Mockito.verify(noteRepository).create(note)
    }

    @Test
    fun `should get a note by id`() {
        val note = Note(id = 1L, title = "Test Title", content = "Test Content")

        Mockito.`when`(noteRepository.findById(1L)).thenReturn(note)

        val result = noteService.getNoteById(1L)

        assertNotNull(result)
        assertEquals(note.id, result?.id)
        assertEquals(note.title, result?.title)
        assertEquals(note.content, result?.content)
        Mockito.verify(noteRepository).findById(1L)
    }

    @Test
    fun `should return null when getting a non-existent note by id`() {
        Mockito.`when`(noteRepository.findById(999L)).thenReturn(null)

        val result = noteService.getNoteById(999L)

        assertNull(result)
        Mockito.verify(noteRepository).findById(999L)
    }

    @Test
    fun `should get all notes`() {
        val notes = listOf(
            Note(id = 1L, title = "Title 1", content = "Content 1"),
            Note(id = 2L, title = "Title 2", content = "Content 2")
        )

        Mockito.`when`(noteRepository.findAll()).thenReturn(notes)

        val result = noteService.getAllNotes()

        assertEquals(2, result.size)
        assertEquals(notes, result)
        Mockito.verify(noteRepository).findAll()
    }

    @Test
    fun `should update a note`() {
        val note = Note(id = null, title = "Updated Title", content = "Updated Content")
        val updatedNote = note.copy(id = 1L)

        Mockito.`when`(noteRepository.update(1L, note)).thenReturn(updatedNote)

        val result = noteService.updateNote(1L, note)

        assertNotNull(result)
        assertEquals(updatedNote.id, result?.id)
        assertEquals(updatedNote.title, result?.title)
        assertEquals(updatedNote.content, result?.content)
        Mockito.verify(noteRepository).update(1L, note)
    }

    @Test
    fun `should return null when updating a non-existent note`() {
        val note = Note(id = null, title = "Updated Title", content = "Updated Content")

        Mockito.`when`(noteRepository.update(999L, note)).thenReturn(null)

        val result = noteService.updateNote(999L, note)

        assertNull(result)
        Mockito.verify(noteRepository).update(999L, note)
    }

    @Test
    fun `should delete a note by id`() {
        Mockito.`when`(noteRepository.delete(1L)).thenReturn(true)

        val result = noteService.deleteNoteById(1L)

        assertTrue(result)
        Mockito.verify(noteRepository).delete(1L)
    }

    @Test
    fun `should return false when deleting a non-existent note`() {
        Mockito.`when`(noteRepository.delete(999L)).thenReturn(false)

        val result = noteService.deleteNoteById(999L)

        assertFalse(result)
        Mockito.verify(noteRepository).delete(999L)
    }
}