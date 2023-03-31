package me.devyonghee.kotlinrealworld.favorite.domain


interface FavoriteRepository {

    fun save(favorite: Favorite): Favorite

    fun findAllBySlug(slug: String): List<Favorite>
}
