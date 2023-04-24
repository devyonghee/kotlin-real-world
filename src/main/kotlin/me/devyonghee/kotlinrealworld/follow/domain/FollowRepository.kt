package me.devyonghee.kotlinrealworld.follow.domain

interface FollowRepository {

    fun save(follow: Follow): Follow

    fun delete(follow: Follow)

    fun exists(follow: Follow): Boolean

    fun findAllByFollowee(followee: String): Collection<Follow>
}
