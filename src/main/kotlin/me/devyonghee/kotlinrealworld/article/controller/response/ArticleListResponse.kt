package me.devyonghee.kotlinrealworld.article.controller.response


data class ArticleListResponse(
    val articles: List<ArticleResponse>,
) {
    val articlesCount: Int
        get() = articles.size
}

