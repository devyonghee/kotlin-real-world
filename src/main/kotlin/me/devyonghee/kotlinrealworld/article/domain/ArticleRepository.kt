package me.devyonghee.kotlinrealworld.article.domain

import java.util.UUID
import org.springframework.data.domain.Pageable

interface ArticleRepository {

    fun save(article: Article): Article

    fun findBySlug(slug: String): Article?

    fun findAll(filter: ArticleFilter, pageable: Pageable): List<Article>

    data class ArticleFilter(
        val author: String?,
        val tagId: UUID?,
        val favorited: String?
    )
}
