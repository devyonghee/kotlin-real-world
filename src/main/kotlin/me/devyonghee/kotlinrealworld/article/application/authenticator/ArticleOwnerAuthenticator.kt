package me.devyonghee.kotlinrealworld.article.application.authenticator

import me.devyonghee.kotlinrealworld.article.domain.ArticleRepository
import me.devyonghee.kotlinrealworld.config.exception.NotFoundElementException
import org.springframework.stereotype.Component

@Component
class ArticleOwnerAuthenticator(
    private val articleRepository: ArticleRepository
) {
    fun isOwner(slug: String, username: String): Boolean {
        return articleRepository.findBySlug(slug)
            ?.let { it.author == username }
            ?: throw NotFoundElementException("article is not exist. article(slug: `${slug}`)")
    }
}