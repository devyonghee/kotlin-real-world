package me.devyonghee.kotlinrealworld.comment

import com.fasterxml.jackson.databind.ObjectMapper
import me.devyonghee.kotlinrealworld.comment.controller.request.CommentRequest
import me.devyonghee.kotlinrealworld.comment.controller.response.CommentResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

fun MockMvc.registerComment(
    token: String,
    slug: String,
    body: String = "body",
    mapper: ObjectMapper
): CommentResponse {
    return post("/api/articles/$slug/comments") {
        header(HttpHeaders.AUTHORIZATION, "Token $token")
        contentType = MediaType.APPLICATION_JSON
        content = mapper.writeValueAsString(CommentRequest(body))
    }.andReturn()
        .response
        .contentAsString
        .let { mapper.readValue(it, CommentResponse::class.java) }
}