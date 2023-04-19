package me.devyonghee.kotlinrealworld.article.persistence.jpa

import me.devyonghee.kotlinrealworld.article.domain.ArticleRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ArticleJpaRepository : JpaRepository<ArticleEntity, String> {

    @Query(
        """
        SELECT article
        FROM ArticleEntity article
        WHERE 1=1
        AND (:#{#filter?.author} IS NULL OR :#{#filter?.author} = article.author)
        AND (:#{#filter?.tagId} IS NULL OR :#{#filter?.tagId} IN (article.tagIds))
        AND (:#{#filter?.favorited} IS NULL OR :#{#filter?.favorited} IN (article.favorites))
    """
    )
    fun findAllByFilter(
        @Param("filter") filter: ArticleRepository.ArticleFilter,
    ): List<ArticleEntity>
}