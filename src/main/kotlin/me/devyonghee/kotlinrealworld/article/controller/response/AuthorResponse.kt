package me.devyonghee.kotlinrealworld.article.controller.response

data class AuthorResponse(
        val username: String,
        val bio: String?,
        val image: String?,
        val following: Boolean,
    )
