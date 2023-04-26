package me.devyonghee.kotlinrealworld.article.persistence

import me.devyonghee.kotlinrealworld.article.domain.Article
import me.devyonghee.kotlinrealworld.article.domain.ArticleRepository
import me.devyonghee.kotlinrealworld.article.persistence.jpa.ArticleEntity
import me.devyonghee.kotlinrealworld.article.persistence.jpa.ArticleJpaRepository
import org.springframework.data.domain.Pageable
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

    override fun findAll(filter: ArticleRepository.ArticleFilter, pageable: Pageable): List<Article> {
        return articleJpaRepository.findAll(filter.author, filter.tagId, filter.favorited, pageable)
            .map { it.toArticle() }
    }

    override fun findAllByAuthorIn(authors: Collection<String>, pageable: Pageable): List<Article> {
        return articleJpaRepository.findAllByAuthorIn(authors, pageable)
            .map { it.toArticle() }
    }

    override fun update(article: Article) {
        entity(article.slug)
            .apply { change(article) }
            .apply { articleJpaRepository.flush() }
    }

    private fun entity(slug: String): ArticleEntity {
        return articleJpaRepository.findByIdOrNull(slug)
            ?: throw IllegalArgumentException("article is not exist. article(slug: `${slug}`)")
    }
}