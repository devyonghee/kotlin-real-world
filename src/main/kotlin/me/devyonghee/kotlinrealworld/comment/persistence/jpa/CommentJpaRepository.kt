package me.devyonghee.kotlinrealworld.comment.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository

interface CommentJpaRepository : JpaRepository<CommentEntity, Long> {

    fun findByArticleId(id: Long): Collection<CommentEntity>
}