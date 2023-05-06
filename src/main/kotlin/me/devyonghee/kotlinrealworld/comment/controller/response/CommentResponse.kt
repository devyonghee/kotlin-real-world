package me.devyonghee.kotlinrealworld.comment.controller.response

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonRootName
import java.time.LocalDateTime
import me.devyonghee.kotlinrealworld.article.controller.response.AuthorResponse
import me.devyonghee.kotlinrealworld.member.domain.Member

@JsonRootName("comment")
data class CommentResponse @JsonCreator constructor(
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