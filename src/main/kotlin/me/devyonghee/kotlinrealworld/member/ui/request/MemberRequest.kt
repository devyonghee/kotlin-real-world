package me.devyonghee.kotlinrealworld.member.ui.request

import jakarta.validation.constraints.NotBlank

data class MemberRequest(
    @NotBlank
    val email: String,
    @NotBlank
    val password: String,
    @NotBlank
    val username: String,
)