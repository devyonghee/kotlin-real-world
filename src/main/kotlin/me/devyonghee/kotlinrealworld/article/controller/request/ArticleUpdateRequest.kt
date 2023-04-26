package me.devyonghee.kotlinrealworld.article.controller.request

import com.fasterxml.jackson.annotation.JsonRootName

@JsonRootName("article")
class ArticleUpdateRequest(
    val title: String?,
    val description: String?,
    val body: String?
)
