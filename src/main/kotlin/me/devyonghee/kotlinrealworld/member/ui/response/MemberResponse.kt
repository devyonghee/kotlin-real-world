package me.devyonghee.kotlinrealworld.account.ui.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class MemberResponse(
    val email: String,
    val token: String,
    val username: String,
    val bio: String?,
    val image: String?,
)