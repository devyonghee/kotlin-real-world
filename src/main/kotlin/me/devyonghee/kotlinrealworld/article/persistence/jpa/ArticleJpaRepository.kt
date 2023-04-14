package me.devyonghee.kotlinrealworld.article.persistence.jpa

import me.devyonghee.kotlinrealworld.article.domain.ArticleRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ArticleJpaRepository : JpaRepository<ArticleEntity, String> {

    @Query(
        """
        SELECT a
        FROM ArticleEntity a
        WHERE (:#{filter.author} IS NULL OR a.author = :#{filter.author})
        AND (:#{filter.tagId} IS NULL OR :#{filter.tagId} IN a.tagList)
    """
    )
    fun findAllByFilter(filter: ArticleRepository.ArticleFilter, pageable: Pageable): List<ArticleEntity>
}