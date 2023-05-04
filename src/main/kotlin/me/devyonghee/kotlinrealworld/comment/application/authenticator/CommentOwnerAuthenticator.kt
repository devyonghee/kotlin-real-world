package me.devyonghee.kotlinrealworld.article.application.authenticator

import me.devyonghee.kotlinrealworld.comment.domain.CommentRepository
import me.devyonghee.kotlinrealworld.config.exception.NotFoundElementException
import org.springframework.stereotype.Component

@Component
class CommentOwnerAuthenticator(
    private val commentRepository: CommentRepository
) {
    fun isOwner(id: Long, username: String): Boolean {
        return commentRepository.findById(id)
            ?.let { it.author == username }
            ?: throw NotFoundElementException("comment is not exist. comment(id: `${id}`)")
    }
}