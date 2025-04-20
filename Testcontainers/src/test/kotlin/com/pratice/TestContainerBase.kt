package com.practice

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Testcontainers
import java.sql.DriverManager

@Testcontainers
open class TestContainerBase {
    companion object {
        private val mysqlContainer = MySQLContainer<Nothing>("mysql:8.4").apply {
            withDatabaseName("testdb")
            withUsername("testuser")
            withPassword("testpass")
        }

        fun getMySQLContainer(): MySQLContainer<Nothing> {
            return mysqlContainer
        }

        @BeforeAll
        @JvmStatic
        fun startContainer() {
            mysqlContainer.start()
            System.setProperty("DB_URL", mysqlContainer.jdbcUrl)
            System.setProperty("DB_USERNAME", mysqlContainer.username)
            System.setProperty("DB_PASSWORD", mysqlContainer.password)

            // Create the 'notes' table
            DriverManager.getConnection(mysqlContainer.jdbcUrl, mysqlContainer.username, mysqlContainer.password).use { connection ->
                connection.createStatement().use { statement ->
                    statement.executeUpdate(
                        """
                        CREATE TABLE notes (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            title VARCHAR(255) NOT NULL,
                            content TEXT NOT NULL
                        )
                        """
                    )
                }
            }
        }

        @AfterAll
        @JvmStatic
        fun stopContainer() {
            mysqlContainer.stop()
        }
    }
}