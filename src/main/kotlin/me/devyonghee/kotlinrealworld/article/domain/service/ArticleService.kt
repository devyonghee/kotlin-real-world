package me.devyonghee.kotlinrealworld.article.domain.service

import me.devyonghee.kotlinrealworld.article.domain.Article
import me.devyonghee.kotlinrealworld.article.domain.ArticleRepository
import me.devyonghee.kotlinrealworld.config.exception.NotFoundElementException
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ArticleService(
    private val articleRepository: ArticleRepository
) {
    fun save(article: Article): Article {
        return articleRepository.save(article)
    }

    fun article(slug: String): Article {
        return articleRepository.findBySlug(slug)
            ?: throw NotFoundElementException("article is not exist. article(slug: `$slug`)")
    }

    fun articles(filter: ArticleRepository.ArticleFilter, page: Pageable): Collection<Article> {
        return articleRepository.findAll(filter, page)
    }

    fun articles(followers: Collection<String>, page: Pageable): Collection<Article> {
        return articleRepository.findAllByAuthorIn(followers, page)
    }

    fun deleteBySlug(slug: String) {
        articleRepository.deleteBySlug(slug)
    }

    fun update(it: Article) {
        articleRepository.update(it)
    }
}
