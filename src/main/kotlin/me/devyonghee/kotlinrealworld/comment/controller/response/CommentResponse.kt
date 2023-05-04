package me.devyonghee.kotlinrealworld.comment.controller.response

import java.time.LocalDateTime
import me.devyonghee.kotlinrealworld.article.controller.response.AuthorResponse
import me.devyonghee.kotlinrealworld.member.domain.Member

data class CommentResponse(
    val id: Long,
    val body: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val author: AuthorResponse
) {
    constructor(
        id: Long,
        body: String,
        createdAt: LocalDateTime,
        updatedAt: LocalDateTime,
        member: Member,
        follwing: Boolean,
    ) : this(
        id,
        body,
        createdAt,
        updatedAt,
        AuthorResponse(member.username, member.bio, member.image, follwing)
    )
}