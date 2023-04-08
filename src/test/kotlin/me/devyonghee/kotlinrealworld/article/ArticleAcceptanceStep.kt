package me.devyonghee.kotlinrealworld.article

import com.fasterxml.jackson.databind.ObjectMapper
import me.devyonghee.kotlinrealworld.article.controller.request.ArticleRequest
import me.devyonghee.kotlinrealworld.article.controller.response.ArticleResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post


fun MockMvc.registerArticle(token: String, request: ArticleRequest, mapper: ObjectMapper): ArticleResponse {
    return post("/api/articles") {
        header(HttpHeaders.AUTHORIZATION, "Token $token")
        contentType = MediaType.APPLICATION_JSON
        content = mapper.writeValueAsString(request)
    }.andReturn()
        .response
        .contentAsString
        .let { mapper.readValue(it, ArticleResponse::class.java) }
}