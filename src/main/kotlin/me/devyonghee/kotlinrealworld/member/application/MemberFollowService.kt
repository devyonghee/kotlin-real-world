package me.devyonghee.kotlinrealworld.member.application

import me.devyonghee.kotlinrealworld.config.exception.InvalidRequestException
import me.devyonghee.kotlinrealworld.follow.application.FollowService
import me.devyonghee.kotlinrealworld.follow.domain.Follow
import me.devyonghee.kotlinrealworld.member.domain.Member
import me.devyonghee.kotlinrealworld.member.ui.response.ProfileResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberFollowService(
    private val memberService: MemberService,
    private val followService: FollowService
) {
    @Transactional
    fun follow(username: String, targetUser: String): ProfileResponse {
        val member: Member = memberService.member(targetUser)
        followService.save(Follow(targetUser, username))
        return ProfileResponse(member, true)
    }

    @Transactional
    fun unfollow(username: String, targetUser: String): ProfileResponse {
        val member: Member = memberService.member(targetUser)
        if (isNotFollowing(targetUser, username)) {
            throw InvalidRequestException("member(username: `$username`) is not following member(username: `$targetUser`)")
        }
        followService.delete(Follow(targetUser, username))
        return ProfileResponse(member)
    }

    private fun isNotFollowing(targetUser: String, username: String) =
        followService.exists(Follow(targetUser, username)).not()
}