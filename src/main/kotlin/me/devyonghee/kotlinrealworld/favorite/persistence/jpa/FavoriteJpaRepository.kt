package me.devyonghee.kotlinrealworld.favorite.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository

internal interface FavoriteJpaRepository : JpaRepository<FavoriteEntity, Long> {

    fun findAllByArticleSlug(slug: String): List<FavoriteEntity>
}