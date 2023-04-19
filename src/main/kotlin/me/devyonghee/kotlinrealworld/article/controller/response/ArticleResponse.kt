package me.devyonghee.kotlinrealworld.article.controller.response

import com.fasterxml.jackson.annotation.JsonRootName
import java.time.LocalDateTime
import me.devyonghee.kotlinrealworld.article.domain.Article
import me.devyonghee.kotlinrealworld.member.domain.Member
import me.devyonghee.kotlinrealworld.tag.domain.Tag

@JsonRootName("article")
data class ArticleResponse(
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
        tags: List<Tag>,
        following: Boolean,
        favorited: Boolean,
        favoritesCount: Int
    ) : this(
        article.slug,
        article.title,
        article.description,
        article.body,
        tags.map { it.name },
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