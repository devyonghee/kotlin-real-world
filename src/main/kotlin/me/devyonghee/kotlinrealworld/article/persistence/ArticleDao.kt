package me.devyonghee.kotlinrealworld.article.persistence

import me.devyonghee.kotlinrealworld.article.domain.Article
import me.devyonghee.kotlinrealworld.article.domain.ArticleRepository
import me.devyonghee.kotlinrealworld.article.persistence.jpa.ArticleEntity
import me.devyonghee.kotlinrealworld.article.persistence.jpa.ArticleJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class ArticleDao(
    private val articleJpaRepository: ArticleJpaRepository
) : ArticleRepository {

    override fun save(article: Article): Article {
        return articleJpaRepository.save(ArticleEntity(article))
            .toArticle()
    }

    override fun findBySlug(slug: String): Article? {
        return articleJpaRepository.findByIdOrNull(slug)?.toArticle()
    }
}