package me.devyonghee.kotlinrealworld.article.domain

import java.util.UUID

data class ArticleFavorite(
    val id: UUID = UUID.randomUUID(),
    val slug: String,
    val favoriteUsername: String
) {
    init {
        require(slug.isNotBlank()) { "'slug' must not be blank" }
        require(favoriteUsername.isNotBlank()) { "'favoriteUsername' must not be blank" }
    }
}