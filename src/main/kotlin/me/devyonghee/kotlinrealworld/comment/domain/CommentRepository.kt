package me.devyonghee.kotlinrealworld.comment.domain

interface CommentRepository {
    fun save(comment: Comment): Comment

    fun findById(id: Long): Comment?

    fun findByArticleId(articleId: Long): Collection<Comment>

    fun delete(id: Long)
}