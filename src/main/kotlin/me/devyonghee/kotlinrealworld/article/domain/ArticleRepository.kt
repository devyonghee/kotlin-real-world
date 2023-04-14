package me.devyonghee.kotlinrealworld.article.domain

import org.springframework.data.domain.Pageable

interface ArticleRepository {

    fun save(article: Article): Article

    fun findBySlug(slug: String): Article?

    fun findAll(filter: ArticleFilter, pageable: Pageable): Article

    data class ArticleFilter(
        val author: String?,
        val tags: List<Int>?,
    )
}
