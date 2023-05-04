package me.devyonghee.kotlinrealworld.follow.application

import me.devyonghee.kotlinrealworld.follow.domain.Follow
import me.devyonghee.kotlinrealworld.follow.domain.FollowRepository
import org.springframework.stereotype.Service

@Service
class FollowService(
    private val followRepository: FollowRepository
) {
    fun save(follow: Follow) {
        followRepository.save(follow)
    }

    fun exists(follow: Follow): Boolean {
        return followRepository.exists(follow)
    }

    fun delete(follow: Follow) {
        return followRepository.delete(follow)
    }

    fun findAllByFollowee(followee: String): Collection<Follow> {
        return followRepository.findAllByFollowee(followee)
    }
}