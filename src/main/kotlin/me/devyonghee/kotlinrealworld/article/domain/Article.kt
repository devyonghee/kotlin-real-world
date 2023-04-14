package me.devyonghee.kotlinrealworld.article.domain

import java.time.LocalDateTime
import java.util.Locale

data class Article(
    val title: String,
    val description: String,
    val body: String,
    val tagList: List<String>,
    val author: String,
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