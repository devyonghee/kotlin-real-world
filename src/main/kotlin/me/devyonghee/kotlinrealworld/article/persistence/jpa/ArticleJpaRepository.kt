package me.devyonghee.kotlinrealworld.article.persistence.jpa

import java.util.UUID
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ArticleJpaRepository : JpaRepository<ArticleEntity, Long> {

    fun findBySlug(slug: String): ArticleEntity?

    fun findAllByAuthorIn(authors: Collection<String>, pageable: Pageable): List<ArticleEntity>

    @Query(
        """
        SELECT article
        FROM ArticleEntity article
        LEFT JOIN article.tagIds tag
        LEFT JOIN article.favorites favorited
        WHERE 1=1
        AND (:author IS NULL OR article.author in (:author))
        AND (:tagId IS NULL OR :tagId = tag)
        AND (:favorited IS NULL OR :favorited = favorited)
    """
    )
    fun findAll(
        @Param("author") author: String?,
        @Param("tagId") tagId: UUID?,
        @Param("favorited") favorited: String?,
        pageable: Pageable
    ): List<ArticleEntity>
}