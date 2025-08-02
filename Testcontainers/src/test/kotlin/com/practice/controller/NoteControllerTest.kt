package com.practice.testcontainer.controller

import com.practice.testcontainer.model.Note
import com.practice.testcontainer.service.NoteService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.mockito.kotlin.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(NoteController::class)
class NoteControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var noteService: NoteService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private lateinit var testNote: Note

    @BeforeEach
    fun setUp() {
        testNote = Note(id = 1L, title = "Test Note", content = "Test Content")
    }

    @Test
    fun `createNote returns 201 CREATED`() {
        // Given
        val newNote = Note(title = "Test Note", content = "Test Content")
        whenever(noteService.createNote(any())).thenReturn(testNote)

        // When & Then
        mockMvc.perform(
            post("/api/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newNote))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.title").value("Test Note"))
            .andExpect(jsonPath("$.content").value("Test Content"))

        verify(noteService).createNote(any())
    }

    @Test
    fun `getNoteById returns 200 OK when note exists`() {
        // Given
        val noteId = 1L
        whenever(noteService.getNoteById(noteId)).thenReturn(testNote)

        // When & Then
        mockMvc.perform(get("/api/notes/{id}", noteId))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.title").value("Test Note"))
            .andExpect(jsonPath("$.content").value("Test Content"))

        verify(noteService).getNoteById(noteId)
    }

    @Test
    fun `getNoteById returns 404 NOT_FOUND when note does not exist`() {
        // Given
        val noteId = 999L
        whenever(noteService.getNoteById(noteId)).thenReturn(null)

        // When & Then
        mockMvc.perform(get("/api/notes/{id}", noteId))
            .andExpect(status().isNotFound)

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

        // When & Then
        mockMvc.perform(get("/api/notes"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].title").value("Test Note"))
            .andExpect(jsonPath("$[1].id").value(2))
            .andExpect(jsonPath("$[1].title").value("Note 2"))

        verify(noteService).getAllNotes()
    }

    @Test
    fun `updateNote returns 200 OK when note is updated`() {
        // Given
        val noteId = 1L
        val updatedNote = Note(title = "Updated Note", content = "Updated Content")
        val returnedNote = Note(id = noteId, title = "Updated Note", content = "Updated Content")
        
        whenever(noteService.updateNote(eq(noteId), any())).thenReturn(returnedNote)

        // When & Then
        mockMvc.perform(
            put("/api/notes/{id}", noteId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedNote))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(noteId))
            .andExpect(jsonPath("$.title").value("Updated Note"))
            .andExpect(jsonPath("$.content").value("Updated Content"))

        verify(noteService).updateNote(eq(noteId), any())
    }

    @Test
    fun `updateNote returns 404 NOT_FOUND when note does not exist`() {
        // Given
        val noteId = 999L
        val updatedNote = Note(title = "Updated Note", content = "Updated Content")
        
        whenever(noteService.updateNote(eq(noteId), any())).thenReturn(null)

        // When & Then
        mockMvc.perform(
            put("/api/notes/{id}", noteId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedNote))
        )
            .andExpect(status().isNotFound)

        verify(noteService).updateNote(eq(noteId), any())
    }

    @Test
    fun `deleteNote returns 204 NO_CONTENT when note is deleted`() {
        // Given
        val noteId = 1L
        whenever(noteService.deleteNoteById(noteId)).thenReturn(true)

        // When & Then
        mockMvc.perform(delete("/api/notes/{id}", noteId))
            .andExpect(status().isNoContent)

        verify(noteService).deleteNoteById(noteId)
    }

    @Test
    fun `deleteNote returns 404 NOT_FOUND when note does not exist`() {
        // Given
        val noteId = 999L
        whenever(noteService.deleteNoteById(noteId)).thenReturn(false)

        // When & Then
        mockMvc.perform(delete("/api/notes/{id}", noteId))
            .andExpect(status().isNotFound)

        verify(noteService).deleteNoteById(noteId)
    }
}