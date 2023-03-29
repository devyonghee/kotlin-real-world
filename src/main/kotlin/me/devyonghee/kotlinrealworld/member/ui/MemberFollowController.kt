package me.devyonghee.kotlinrealworld.member.ui

import me.devyonghee.kotlinrealworld.member.application.MemberFollowService
import me.devyonghee.kotlinrealworld.member.ui.response.ProfileResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberFollowController(
    private val memberFollowService: MemberFollowService
) {

    @PostMapping("/api/profiles/{username}/follow")
    fun follow(
        @AuthenticationPrincipal userDetails: UserDetails,
        @PathVariable username: String
    ): ResponseEntity<ProfileResponse> {
        return ResponseEntity.ok(memberFollowService.follow(userDetails.username, username))
    }

    @DeleteMapping("/api/profiles/{username}/follow")
    fun unfollow(
        @AuthenticationPrincipal userDetails: UserDetails,
        @PathVariable username: String
    ): ResponseEntity<ProfileResponse> {
        return ResponseEntity.ok(memberFollowService.unfollow(userDetails.username, username))
    }
}