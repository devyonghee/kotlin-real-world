package me.devyonghee.kotlinrealworld.article.persistence.jpa

import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID
import me.devyonghee.kotlinrealworld.article.domain.Article
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@Table(name = "article")
@EntityListeners(AuditingEntityListener::class)
class ArticleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(unique = true)
    var slug: String,
    var title: String,
    var description: String,
    var body: String,
    val author: String,
    @ElementCollection
    @JoinColumn(name = "article_id")
    @CollectionTable(name = "article_tag")
    val tagIds: List<UUID> = emptyList(),
    @ElementCollection
    @JoinColumn(name = "article_id")
    @CollectionTable(name = "article_favorite")
    val favorites: List<String> = emptyList(),
    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    constructor(article: Article) : this(
        id = article.id,
        slug = article.slug,
        title = article.title,
        description = article.description,
        body = article.body,
        favorites = article.favorites,
        author = article.author,
        tagIds = article.tagIds,
    )

    fun toArticle() = Article(
        id = id,
        slug = slug,
        title = title,
        description = description,
        body = body,
        favorites = favorites,
        author = author,
        tagIds = tagIds,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

    fun change(article: Article) {
        this.slug = article.slug
        this.title = article.title
        this.description = article.description
        this.body = article.body
        this.updatedAt = LocalDateTime.now()
    }
}