package me.devyonghee.kotlinrealworld.article.controller.request

import com.fasterxml.jackson.annotation.JsonRootName
import jakarta.validation.constraints.NotBlank

@JsonRootName("article")

class ArticleRequest(
    @NotBlank
    val title: String,
    val description: String,
    val body: String,
    val tagList: List<String> = emptyList(),
)
