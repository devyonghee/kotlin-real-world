package me.devyonghee.kotlinrealworld.article.persistence.jpa

import jakarta.persistence.CollectionTable
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import me.devyonghee.kotlinrealworld.article.domain.Article
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity(name = "article")
@EntityListeners(AuditingEntityListener::class)
class ArticleEntity(
    @Id
    val slug: String,
    val title: String,
    val description: String,
    val body: String,
    val author: String,
    @ElementCollection
    @JoinColumn(name = "article_slug")
    @CollectionTable(name = "article_tag")
    val tagList: List<String> = emptyList(),
    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @LastModifiedDate
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    constructor(article: Article) : this(
        slug = article.slug,
        title = article.title,
        description = article.description,
        body = article.body,
        author = article.author,
        tagList = article.tagList,
    )

    fun toArticle() = Article(
        slug = slug,
        title = title,
        description = description,
        body = body,
        author = author,
        tagList = tagList,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}