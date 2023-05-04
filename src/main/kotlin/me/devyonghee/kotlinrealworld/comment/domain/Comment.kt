package me.devyonghee.kotlinrealworld.comment.domain

import java.time.LocalDateTime

data class Comment(
    val body: String,
    val author: String,
    val articleId: Long,
    val id: Long = 0,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)