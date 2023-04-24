package me.devyonghee.kotlinrealworld.article.domain

import java.util.UUID
import org.springframework.data.domain.Pageable

interface ArticleRepository {

    fun save(article: Article): Article

    fun findBySlug(slug: String): Article?

    fun findAll(filter: ArticleFilter, pageable: Pageable): List<Article>

    fun findAllByAuthorIn(authors: Collection<String>, pageable: Pageable): List<Article>

    data class ArticleFilter(
        val author: String? = null,
        val tagId: UUID? = null,
        val favorited: String? = null
    )
}
