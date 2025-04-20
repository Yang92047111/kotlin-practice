package com.practice.repository

import com.practice.TestContainerBase
import com.practice.model.Note
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.h2.H2DatabasePlugin
import org.jdbi.v3.sqlobject.SqlObjectPlugin
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
class JdbiNoteRepositoryTest : TestContainerBase() {

    private lateinit var jdbi: Jdbi
    private lateinit var repository: JdbiNoteRepository

    @BeforeEach
    fun setUp() {
        val mysqlContainer = TestContainerBase.getMySQLContainer()
        jdbi = Jdbi.create(mysqlContainer.jdbcUrl, mysqlContainer.username, mysqlContainer.password)
            .installPlugin(SqlObjectPlugin())
            .installPlugin(H2DatabasePlugin())
        jdbi.registerRowMapper(NoteMapper())
        repository = JdbiNoteRepository(jdbi)
    }

    @Test
    fun `should create a note`() {
        val note = Note(id = null, title = "Test Title", content = "Test Content")
        val createdNote = repository.create(note)

        assertNotNull(createdNote.id)
        assertEquals(note.title, createdNote.title)
        assertEquals(note.content, createdNote.content)
    }

    @Test
    fun `should find a note by id`() {
        val note = Note(id = null, title = "Test Title", content = "Test Content")
        val createdNote = repository.create(note)

        val foundNote = repository.findById(createdNote.id!!)
        assertNotNull(foundNote)
        assertEquals(createdNote.id, foundNote?.id)
        assertEquals(createdNote.title, foundNote?.title)
        assertEquals(createdNote.content, foundNote?.content)
    }

    @Test
    fun `should return null when finding a non-existent note`() {
        val foundNote = repository.findById(999L)
        assertNull(foundNote)
    }

    @Test
    fun `should find all notes`() {
        val note1 = Note(id = null, title = "Title 1", content = "Content 1")
        val note2 = Note(id = null, title = "Title 2", content = "Content 2")
        repository.create(note1)
        repository.create(note2)

        val notes = repository.findAll()
        assertEquals(2, notes.size)
    }

    @Test
    fun `should update a note`() {
        val note = Note(id = null, title = "Old Title", content = "Old Content")
        val createdNote = repository.create(note)

        val updatedNote = repository.update(createdNote.id!!, Note(id = null, title = "New Title", content = "New Content"))
        assertNotNull(updatedNote)
        assertEquals("New Title", updatedNote?.title)
        assertEquals("New Content", updatedNote?.content)
    }

    @Test
    fun `should return null when updating a non-existent note`() {
        val updatedNote = repository.update(999L, Note(id = null, title = "New Title", content = "New Content"))
        assertNull(updatedNote)
    }

    @Test
    fun `should delete a note`() {
        val note = Note(id = null, title = "Test Title", content = "Test Content")
        val createdNote = repository.create(note)

        val isDeleted = repository.delete(createdNote.id!!)
        assertTrue(isDeleted)

        val foundNote = repository.findById(createdNote.id)
        assertNull(foundNote)
    }

    @Test
    fun `should return false when deleting a non-existent note`() {
        val isDeleted = repository.delete(999L)
        assertFalse(isDeleted)
    }
}