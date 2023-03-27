package me.devyonghee.kotlinrealworld.member.ui.request

import com.fasterxml.jackson.annotation.JsonRootName
import java.net.URI

@JsonRootName("user")
data class MemberUpdateRequest(
    val email: String?,
    val password: String?,
    val username: String?,
    val bio: String?,
    val image: URI?,
)