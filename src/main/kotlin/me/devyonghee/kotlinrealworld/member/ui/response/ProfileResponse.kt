package me.devyonghee.kotlinrealworld.member.ui.response

import com.fasterxml.jackson.annotation.JsonRootName
import me.devyonghee.kotlinrealworld.member.domain.Member

@JsonRootName("profile")
data class ProfileResponse(
    val username: String,
    val bio: String?,
    val image: String?,
    val following: Boolean = false
) {
    constructor(member: Member, following: Boolean) : this(
        username = member.username,
        bio = member.bio,
        image = member.image,
        following = following
    )

    constructor(member: Member) : this(
        username = member.username,
        bio = member.bio,
        image = member.image,
    )
}
