package me.devyonghee.kotlinrealworld.article.domain

interface ArticleFavoriteRepository {

    fun findBySlug(slug: String): List<ArticleFavorite>
}