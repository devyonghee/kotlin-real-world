package me.devyonghee.kotlinrealworld.favorite.application

import me.devyonghee.kotlinrealworld.favorite.domain.Favorite
import me.devyonghee.kotlinrealworld.favorite.domain.FavoriteRepository
import org.springframework.stereotype.Service

@Service
class FavoriteService(
    private val favoriteRepository: FavoriteRepository
) {
    fun findAll(slug: String): List<Favorite> {
        return favoriteRepository.findAllBySlug(slug)
    }
}