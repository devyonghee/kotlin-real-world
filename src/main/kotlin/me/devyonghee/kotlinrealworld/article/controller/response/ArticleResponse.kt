package me.devyonghee.kotlinrealworld.article.controller.response

import me.devyonghee.kotlinrealworld.article.domain.Article
import me.devyonghee.kotlinrealworld.member.domain.Member
import java.time.LocalDateTime

class ArticleResponse(
    val slug: String,
    val title: String,
    val description: String,
    val body: String,
    val tagList: List<String>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val favorited: Boolean,
    val favoritesCount: Int,
    val author: Author,
) {
    constructor(
        article: Article,
        member: Member,
        following: Boolean,
        favorited: Boolean,
        favoritesCount: Int
    ) : this(
        article.slug,
        article.title,
        article.description,
        article.body,
        article.tagList,
        article.createdAt,
        article.updatedAt,
        favorited,
        favoritesCount,
        Author(member.username, member.bio, member.image, following)
    )

    data class Author(
        val username: String,
        val bio: String?,
        val image: String?,
        val following: Boolean,
    )
}