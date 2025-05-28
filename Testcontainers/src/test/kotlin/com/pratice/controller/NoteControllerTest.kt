package com.practice.testcontainer.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.practice.testcontainer.model.Note
import com.practice.testcontainer.service.NoteService
import com.practice.testcontainer.NotesCrudApplication
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.context.ContextConfiguration

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NoteControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var noteService: NoteService

    // @Test
    // fun `should create a note`() {
    //     val note = Note(id = null, title = "Test Title", content = "Test Content")
    //     val createdNote = note.copy(id = 1L)

    //     Mockito.doReturn(createdNote).`when`(noteService).createNote(note)

    //     mockMvc.perform(
    //         post("/api/notes")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(objectMapper.writeValueAsString(note))
    //     )
    //         .andExpect(status().isCreated)
    //         .andExpect(jsonPath("$.id").value(1L))
    //         .andExpect(jsonPath("$.title").value("Test Title"))
    //         .andExpect(jsonPath("$.content").value("Test Content"))

    //     verify(noteService).createNote(note)
    // }

    // @Test
    // fun `should get a note by id`() {
    //     val note = Note(id = 1L, title = "Test Title", content = "Test Content")

    //     `when`(noteService.getNoteById(1L)).thenReturn(note)

    //     mockMvc.perform(get("/api/notes/1"))
    //         .andExpect(status().isOk)
    //         .andExpect(jsonPath("$.id").value(1L))
    //         .andExpect(jsonPath("$.title").value("Test Title"))
    //         .andExpect(jsonPath("$.content").value("Test Content"))

    //     verify(noteService).getNoteById(1L)
    // }

    // @Test
    // fun `should return 404 when getting a non-existent note by id`() {
    //     `when`(noteService.getNoteById(999L)).thenReturn(null)

    //     mockMvc.perform(get("/api/notes/999"))
    //         .andExpect(status().isNotFound)

    //     verify(noteService).getNoteById(999L)
    // }

    // @Test
    // fun `should get all notes`() {
    //     val notes = listOf(
    //         Note(id = 1L, title = "Title 1", content = "Content 1"),
    //         Note(id = 2L, title = "Title 2", content = "Content 2")
    //     )

    //     `when`(noteService.getAllNotes()).thenReturn(notes)

    //     mockMvc.perform(get("/api/notes"))
    //         .andExpect(status().isOk)
    //         .andExpect(jsonPath("$.size()").value(2))
    //         .andExpect(jsonPath("$[0].id").value(1L))
    //         .andExpect(jsonPath("$[1].id").value(2L))

    //     verify(noteService).getAllNotes()
    // }

    // @Test
    // fun `should update a note`() {
    //     val note = Note(id = null, title = "Updated Title", content = "Updated Content")
    //     val updatedNote = note.copy(id = 1L)

    //     `when`(noteService.updateNote(1L, note)).thenReturn(updatedNote)

    //     mockMvc.perform(
    //         put("/api/notes/1")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(objectMapper.writeValueAsString(note))
    //     )
    //         .andExpect(status().isOk)
    //         .andExpect(jsonPath("$.id").value(1L))
    //         .andExpect(jsonPath("$.title").value("Updated Title"))
    //         .andExpect(jsonPath("$.content").value("Updated Content"))

    //     verify(noteService).updateNote(1L, note)
    // }

    // @Test
    // fun `should return 404 when updating a non-existent note`() {
    //     val note = Note(id = null, title = "Updated Title", content = "Updated Content")

    //     `when`(noteService.updateNote(999L, note)).thenReturn(null)

    //     mockMvc.perform(
    //         put("/api/notes/999")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(objectMapper.writeValueAsString(note))
    //     )
    //         .andExpect(status().isNotFound)

    //     verify(noteService).updateNote(999L, note)
    // }

    // @Test
    // fun `should delete a note by id`() {
    //     `when`(noteService.deleteNoteById(1L)).thenReturn(true)

    //     mockMvc.perform(delete("/api/notes/1"))
    //         .andExpect(status().isNoContent)

    //     verify(noteService).deleteNoteById(1L)
    // }

    // @Test
    // fun `should return 404 when deleting a non-existent note`() {
    //     `when`(noteService.deleteNoteById(999L)).thenReturn(false)

    //     mockMvc.perform(delete("/api/notes/999"))
    //         .andExpect(status().isNotFound)

    //     verify(noteService).deleteNoteById(999L)
    // }
}