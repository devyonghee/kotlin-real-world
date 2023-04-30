package me.devyonghee.kotlinrealworld.comment.controller.response

import java.time.LocalDateTime
import me.devyonghee.kotlinrealworld.article.controller.response.AuthorResponse

data class CommentResponse(
    val id: Long,
    val body: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val author: AuthorResponse
)