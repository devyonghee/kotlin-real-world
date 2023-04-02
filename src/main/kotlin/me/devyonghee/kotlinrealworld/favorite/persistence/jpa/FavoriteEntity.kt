package me.devyonghee.kotlinrealworld.favorite.persistence.jpa

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import me.devyonghee.kotlinrealworld.favorite.domain.Favorite

@Entity(name = "favorite")
class FavoriteEntity(
    private val articleSlug: String,
    private val username: String,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
) {

    fun toDomain(): Favorite {
        return Favorite(articleSlug, username, id)
    }

    constructor(favorite: Favorite) : this(favorite.articleSlug, favorite.username)
}
