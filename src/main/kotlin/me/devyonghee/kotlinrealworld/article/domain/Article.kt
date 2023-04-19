package me.devyonghee.kotlinrealworld.article.domain

import java.time.LocalDateTime
import java.util.Locale
import java.util.UUID

data class Article(
    val title: String,
    val description: String,
    val body: String,
    val tagIds: List<UUID>,
    val author: String,
    val favorites: List<String> = emptyList(),
    val slug: String = title.replace(" ", "-")
        .lowercase(Locale.getDefault()),
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {

    init {
        require(title.isNotBlank()) { "'title' must not be blank" }
        require(description.isNotBlank()) { "'description' must not be blank" }
        require(author.isNotBlank()) { "'author' must not be blank" }
    }
}