package me.devyonghee.kotlinrealworld.comment.controller.request

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonRootName
import jakarta.validation.constraints.NotEmpty

@JsonRootName("comment")
data class CommentRequest @JsonCreator constructor(
    @NotEmpty
    val body: String
)