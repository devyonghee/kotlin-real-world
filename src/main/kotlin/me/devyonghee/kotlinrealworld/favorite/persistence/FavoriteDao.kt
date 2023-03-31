package me.devyonghee.kotlinrealworld.favorite.persistence

import me.devyonghee.kotlinrealworld.favorite.domain.Favorite
import me.devyonghee.kotlinrealworld.favorite.domain.FavoriteRepository
import me.devyonghee.kotlinrealworld.favorite.persistence.jpa.FavoriteEntity
import me.devyonghee.kotlinrealworld.favorite.persistence.jpa.FavoriteJpaRepository
import org.springframework.stereotype.Repository

@Repository
internal class FavoriteDao(
    private val favoriteJpaRepository: FavoriteJpaRepository
) : FavoriteRepository {

    override fun save(favorite: Favorite): Favorite {
        return favoriteJpaRepository.save(FavoriteEntity(favorite)).toDomain()
    }

    override fun findAllBySlug(slug: String): List<Favorite> {
        return favoriteJpaRepository.findAllByArticleSlug(slug).map { it.toDomain() }
    }
}