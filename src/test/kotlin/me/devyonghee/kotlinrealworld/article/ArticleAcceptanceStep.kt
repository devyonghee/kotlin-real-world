package me.devyonghee.kotlinrealworld.article

import com.fasterxml.jackson.databind.ObjectMapper
import me.devyonghee.kotlinrealworld.account.ui.request.AccountRequest
import me.devyonghee.kotlinrealworld.account.ui.response.AccountResponse
import me.devyonghee.kotlinrealworld.article.controller.request.ArticleRequest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post


fun MockMvc.registerArticle(request: ArticleRequest, mapper: ObjectMapper): AccountResponse {
    return post("/api/users") {
        contentType = MediaType.APPLICATION_JSON
        content = mapper.writeValueAsString(request)
    }.andReturn()
        .response
        .contentAsString
        .let { mapper.readValue(it, AccountResponse::class.java) }
}