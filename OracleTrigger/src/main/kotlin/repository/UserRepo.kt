package com.practice.oracle.repository

import org.springframework.stereotype.Repository
import javax.sql.DataSource
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.PreparedStatementCreator
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import com.practice.oracle.model.User

@Repository
class UserRepository(private val dataSource: DataSource) {

    private val jdbcTemplate: JdbcTemplate = JdbcTemplate(dataSource)

    fun findAll(): List<User> {
        val sql = "SELECT * FROM your_table"
        return jdbcTemplate.query(sql, rowMapper)
    }

    fun findById(id: Long): User? {
        val sql = "SELECT * FROM your_table WHERE id = ?"
        return jdbcTemplate.queryForObject(sql, rowMapper, id)
    }

    fun insert(user: User) {
        val sql = "INSERT INTO your_table (name, email) VALUES (?, ?)"
        jdbcTemplate.update(sql, user.name, user.email)
    }

    fun update(user: User) {
        val sql = "UPDATE your_table SET name = ?, email = ? WHERE id = ?"
        jdbcTemplate.update(sql, user.name, user.email, user.id)
    }

    fun delete(id: Long) {
        val user = findById(id)
        if (user != null) {
            val sql = "DELETE FROM your_table WHERE id = ?"
            jdbcTemplate.update(sql, id)
        }
    }

    private val rowMapper: RowMapper<User> = RowMapper { rs: ResultSet, _: Int ->
        User(
            id = rs.getLong("id"),
            name = rs.getString("name"),
            email = rs.getString("emmail")
        )
    }
}