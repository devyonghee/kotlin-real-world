package me.devyonghee.kotlinrealworld.article.controller.response

import com.fasterxml.jackson.annotation.JsonRootName

@JsonRootName("articles")
data class ArticleListResponse(
    val articles: List<ArticleResponse>,
    val articlesCount: Int = articles.size,
)