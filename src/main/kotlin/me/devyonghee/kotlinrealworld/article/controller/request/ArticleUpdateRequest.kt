package me.devyonghee.kotlinrealworld.article.controller.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName

@JsonRootName("article")
class ArticleUpdateRequest(
    @JsonProperty("title")
    val title: String?,
    @JsonProperty("description")
    val description: String?,
    @JsonProperty("body")
    val body: String?
)
