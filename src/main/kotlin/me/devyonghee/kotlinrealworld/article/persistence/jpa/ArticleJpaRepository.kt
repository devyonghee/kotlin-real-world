package me.devyonghee.kotlinrealworld.article.persistence.jpa

import me.devyonghee.kotlinrealworld.article.domain.ArticleRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ArticleJpaRepository : JpaRepository<ArticleEntity, String> {

    @Query(
        """
        SELECT article
        FROM ArticleEntity article
        LEFT JOIN article.tagIds tag
        LEFT JOIN article.favorites favorited
        WHERE 1=1
        AND (:#{#filter.author} IS NULL OR :#{#filter.author} = article.author)
        AND (:#{#filter.tagId} IS NULL OR :#{#filter.tagId} = tag)
        AND (:#{#filter.favorited} IS NULL OR :#{#filter.favorited} = favorited)
    """
    )
    fun findAllByFilter(
        filter: ArticleRepository.ArticleFilter,
        pageable: Pageable
    ): List<ArticleEntity>
}