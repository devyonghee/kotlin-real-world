package me.devyonghee.kotlinrealworld.comment.persistence.jpa

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import me.devyonghee.kotlinrealworld.comment.domain.Comment

@Entity
@Table(name = "comment")
class CommentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val body: String,
    val author: String,
    val articleId: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    constructor(comment: Comment) : this(
        id = comment.id,
        body = comment.body,
        author = comment.author,
        articleId = comment.articleId,
        createdAt = comment.createdAt,
        updatedAt = comment.updatedAt
    )

    fun toDomain(): Comment {
        return Comment(
            id = id,
            body = body,
            author = author,
            articleId = articleId,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}
