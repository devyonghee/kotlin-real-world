package me.devyonghee.kotlinrealworld.member.domain

data class Member(
    val username: String,
    val email: String,
    val image: String? = null,
    val bio: String? = null
){
    init {
        require(username.isNotBlank()) { "'username' must not be blank" }
        require(email.isNotBlank()) { "'email' must not be blank" }
    }
}