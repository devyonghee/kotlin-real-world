package me.devyonghee.kotlinrealworld.comment.controller.request

import com.fasterxml.jackson.annotation.JsonRootName

@JsonRootName("comment")
data class CommentRequest(
    val body: String
)