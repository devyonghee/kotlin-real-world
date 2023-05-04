package me.devyonghee.kotlinrealworld.comment.persistence

import me.devyonghee.kotlinrealworld.comment.domain.Comment
import me.devyonghee.kotlinrealworld.comment.domain.CommentRepository
import me.devyonghee.kotlinrealworld.comment.persistence.jpa.CommentEntity
import me.devyonghee.kotlinrealworld.comment.persistence.jpa.CommentJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class CommentDao(
    private val jpaRepository: CommentJpaRepository
) : CommentRepository {
    override fun save(comment: Comment): Comment {
        return jpaRepository.save(CommentEntity(comment)).toDomain()
    }

    override fun findById(id: Long): Comment? {
        return jpaRepository.findByIdOrNull(id)?.toDomain()
    }

    override fun findByArticleId(articleId: Long): Collection<Comment> {
        return jpaRepository.findByArticleId(articleId)
            .map { it.toDomain() }
    }

    override fun delete(id: Long) {
        jpaRepository.deleteById(id)
    }
}