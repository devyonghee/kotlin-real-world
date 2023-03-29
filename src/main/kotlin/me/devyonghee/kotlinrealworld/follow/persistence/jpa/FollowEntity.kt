package me.devyonghee.kotlinrealworld.follow.persistence.jpa

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import me.devyonghee.kotlinrealworld.follow.domain.Follow

@Entity(name = "follow")
class FollowEntity(
    private val followerUsername: String,
    private val followeeUsername: String,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0,
) {
    constructor(followerUsername: Follow) : this(
        followerUsername.followerUsername,
        followerUsername.followeeUsername,
        followerUsername.id
    )

    fun toDomain(): Follow {
        return Follow(followerUsername, followeeUsername, id)
    }
}