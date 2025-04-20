package com.practice.repository

import com.practice.model.Note
import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import java.sql.ResultSet

class NoteMapper : RowMapper<Note> {
    override fun map(rs: ResultSet, ctx: StatementContext): Note {
        return Note(
            id = rs.getLong("id"),
            title = rs.getString("title"),
            content = rs.getString("content")
        )
    }
}