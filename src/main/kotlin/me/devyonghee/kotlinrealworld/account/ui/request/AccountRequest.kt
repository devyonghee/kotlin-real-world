package me.devyonghee.kotlinrealworld.account.ui.request

import com.fasterxml.jackson.annotation.JsonRootName
import jakarta.validation.constraints.NotBlank

@JsonRootName("user")
data class AccountRequest(
    @NotBlank
    val email: String,
    @NotBlank
    val password: String,
    @NotBlank
    val username: String,
)