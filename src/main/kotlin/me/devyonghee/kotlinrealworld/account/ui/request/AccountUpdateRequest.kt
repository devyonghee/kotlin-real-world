package me.devyonghee.kotlinrealworld.account.ui.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName
import me.devyonghee.kotlinrealworld.account.domain.Account
import org.springframework.security.crypto.password.PasswordEncoder

@JsonRootName("user")
data class AccountUpdateRequest(
    @JsonProperty("email")
    val email: String? = null,
    @JsonProperty("password")
    val password: String? = null,
    @JsonProperty("username")
    val username: String? = null,
    @JsonProperty("bio")
    val bio: String? = null,
    @JsonProperty("image")
    val image: String? = null,
) {

}