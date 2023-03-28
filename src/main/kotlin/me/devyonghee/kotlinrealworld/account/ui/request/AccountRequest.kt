package me.devyonghee.kotlinrealworld.account.ui.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName
import jakarta.validation.constraints.NotBlank

@JsonRootName("user")
data class AccountRequest(
    @NotBlank
    @JsonProperty("email")
    val email: String,
    @NotBlank
    @JsonProperty("password")
    val password: String,
    @NotBlank
    @JsonProperty("username")
    val username: String,
)