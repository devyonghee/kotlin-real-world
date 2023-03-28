package me.devyonghee.kotlinrealworld.account.ui.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName

@JsonRootName("user")
data class AccountResponse(
    @JsonProperty("email")
    val email: String,
    val token: String,
    val username: String,
    val bio: String? = null,
    val image: String? = null
)