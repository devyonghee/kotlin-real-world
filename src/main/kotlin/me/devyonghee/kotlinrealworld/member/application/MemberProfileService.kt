package me.devyonghee.kotlinrealworld.member.application

import me.devyonghee.kotlinrealworld.follow.application.FollowService
import me.devyonghee.kotlinrealworld.follow.domain.Follow
import me.devyonghee.kotlinrealworld.member.domain.Member
import me.devyonghee.kotlinrealworld.member.ui.response.ProfileResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberProfileService(
    private val memberService: MemberService,
    private val followService: FollowService
) {
    fun profile(targetUsername: String): ProfileResponse {
        return ProfileResponse(memberService.member(targetUsername))
    }

    fun profile(username: String, targetUsername: String): ProfileResponse {
        val member: Member = memberService.member(targetUsername)
        return ProfileResponse(member, followService.exists(Follow(username, targetUsername)))
    }
}