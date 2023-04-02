package me.devyonghee.kotlinrealworld.favorite.domain

data class Favorite(
    val articleSlug: String,
    val username: String,
    val id: Long = 0
)