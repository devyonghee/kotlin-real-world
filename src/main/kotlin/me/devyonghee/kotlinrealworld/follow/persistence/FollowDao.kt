package me.devyonghee.kotlinrealworld.follow.persistence

import me.devyonghee.kotlinrealworld.follow.domain.Follow
import me.devyonghee.kotlinrealworld.follow.domain.FollowRepository
import me.devyonghee.kotlinrealworld.follow.persistence.jpa.FollowEntity
import me.devyonghee.kotlinrealworld.follow.persistence.jpa.FollowJpaRepository
import org.springframework.stereotype.Repository

@Repository
internal class FollowDao(
    private val followJpaRepository: FollowJpaRepository
) : FollowRepository {

    override fun save(follow: Follow): Follow {
        return followJpaRepository.save(FollowEntity(follow)).toDomain()
    }

    override fun delete(follow: Follow) {
        followJpaRepository
            .deleteByFolloweeUsernameAndFollowerUsername(follow.followeeUsername, follow.followerUsername)
    }

    override fun exists(follow: Follow): Boolean {
        return followJpaRepository
            .existsByFolloweeUsernameAndFollowerUsername(follow.followeeUsername, follow.followerUsername)
    }

    override fun findAllByFollowee(followee: String): Collection<Follow> {
        return followJpaRepository.findAllByFolloweeUsername(followee)
            .map { it.toDomain() }
    }
}
