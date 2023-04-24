package me.devyonghee.kotlinrealworld.follow.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository

internal interface FollowJpaRepository : JpaRepository<FollowEntity, Long> {

    fun existsByFolloweeUsernameAndFollowerUsername(followeeUsername: String, followerUsername: String): Boolean

    fun deleteByFolloweeUsernameAndFollowerUsername(followeeUsername: String, followerUsername: String)

    fun findAllByFolloweeUsername(followeeUsername: String): Collection<FollowEntity>
}