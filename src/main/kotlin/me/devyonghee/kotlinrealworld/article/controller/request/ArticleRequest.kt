package me.devyonghee.kotlinrealworld.article.controller.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName
import jakarta.validation.constraints.NotBlank

@JsonRootName("article")
data class ArticleRequest(
    @NotBlank
    @JsonProperty("title")
    val title: String,
    @JsonProperty("description")
    val description: String,
    @JsonProperty("body")
    val body: String,
    @JsonProperty("tagList")
    val tagList: List<String> = emptyList(),
)
