package me.devyonghee.kotlinrealworld

import io.kotest.core.listeners.TestListener
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import org.springframework.jdbc.core.JdbcTemplate

@Suppress("SqlNoDataSourceInspection", "SqlResolve")
class DatabaseAfterEachCleanup(
    private val jdbcTemplate: JdbcTemplate
) : TestListener {

    override suspend fun afterEach(testCase: TestCase, result: TestResult) {
        with(jdbcTemplate) {
            update("SET REFERENTIAL_INTEGRITY FALSE")
            update(
                """
                TRUNCATE TABLE article;
                TRUNCATE TABLE article_tag;
                TRUNCATE TABLE article_favorite;
                TRUNCATE TABLE comment;
                TRUNCATE TABLE tag;
                TRUNCATE TABLE follow;
                TRUNCATE TABLE account;
                TRUNCATE TABLE member;
                """.trimIndent()
            )

            queryForList(
                """SELECT "SEQUENCE_NAME" FROM "INFORMATION_SCHEMA"."SEQUENCES" WHERE "SEQUENCE_SCHEMA" = 'public';""".trimIndent(),
                String::class.java
            ).joinToString("\n") {
                """ALTER SEQUENCE "$it" RESTART WITH 1;"""
            }.also { update(it) }

            update("SET REFERENTIAL_INTEGRITY TRUE")
        }
    }
}
