package me.devyonghee.kotlinrealworld.article.domain

interface ArticleRepository {

    fun save(article: Article):Article

    fun findBySlug(slug: String): Article?
}
