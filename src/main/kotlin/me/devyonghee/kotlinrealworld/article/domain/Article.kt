package me.devyonghee.kotlinrealworld.article.domain

import java.time.LocalDateTime
import java.util.Locale
import java.util.UUID


private fun slug(string: String): String {
    return string.replace(" ", "-")
        .lowercase(Locale.getDefault())
}

data class Article(
    val title: String,
    val description: String,
    val body: String,
    val tagIds: List<UUID>,
    val author: String,
    val favorites: List<String> = emptyList(),
    val slug: String = slug(title),
    val id: Long = 0,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    init {
        require(title.isNotBlank()) { "'title' must not be blank" }
        require(description.isNotBlank()) { "'description' must not be blank" }
        require(author.isNotBlank()) { "'author' must not be blank" }
    }

    fun changedBody(body: String): Article {
        return copy(body = body, updatedAt = LocalDateTime.now())
    }

    fun changedTitle(title: String): Article {
        return copy(
            slug = slug(title),
            title = title,
            updatedAt = LocalDateTime.now()
        )
    }

    fun changedDescription(description: String): Article {
        return copy(description = description, updatedAt = LocalDateTime.now())
    }
}