package com.practice.testcontainer.repository

import com.practice.testcontainer.model.Note
import com.practice.testcontainer.repository.NoteRepository
import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Repository

@Repository
open class JdbiNoteRepository(private val jdbi: Jdbi) : NoteRepository {

    override fun create(note: Note): Note {
        val id = jdbi.withHandle<Long, Exception> { handle ->
            handle.createUpdate("INSERT INTO notes (title, content) VALUES (:title, :content)")
                .bind("title", note.title)
                .bind("content", note.content)
                .executeAndReturnGeneratedKeys("id")
                .mapTo(Long::class.java)
                .one()
        }
        return note.copy(id = id)
    }

    override fun findById(id: Long): Note? {
        return jdbi.withHandle<Note?, Exception> { handle ->
            handle.createQuery("SELECT * FROM notes WHERE id = :id")
                .bind("id", id)
                .mapTo(Note::class.java)
                .findOne()
                .orElse(null)
        }
    }

    override fun findAll(): List<Note> {
        return jdbi.withHandle<List<Note>, Exception> { handle ->
            handle.createQuery("SELECT * FROM notes")
                .mapTo(Note::class.java)
                .list()
        }
    }

    override fun update(id: Long, note: Note): Note? {
        val rowsUpdated = jdbi.withHandle<Int, Exception> { handle ->
            handle.createUpdate("UPDATE notes SET title = :title, content = :content WHERE id = :id")
                .bind("title", note.title)
                .bind("content", note.content)
                .bind("id", id)
                .execute()
        }
        return if (rowsUpdated > 0) findById(id) else null
    }

    override fun delete(id: Long): Boolean {
        val rowsDeleted = jdbi.withHandle<Int, Exception> { handle ->
            handle.createUpdate("DELETE FROM notes WHERE id = :id")
                .bind("id", id)
                .execute()
        }
        return rowsDeleted > 0
    }
}