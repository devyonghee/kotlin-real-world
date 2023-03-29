package me.devyonghee.kotlinrealworld.member.ui

import me.devyonghee.kotlinrealworld.member.application.MemberProfileService
import me.devyonghee.kotlinrealworld.member.ui.response.ProfileResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberProfileController(
    private val memberProfileService: MemberProfileService
) {

    @GetMapping("/api/profiles/{username}")
    fun profile(
        @AuthenticationPrincipal userDetails: UserDetails?,
        @PathVariable username: String
    ): ResponseEntity<ProfileResponse> {
        return ResponseEntity.ok(userDetails?.let {
            memberProfileService.profile(it.username, username)
        } ?: memberProfileService.profile(username))
    }
}