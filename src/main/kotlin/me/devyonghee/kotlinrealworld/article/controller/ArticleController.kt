package me.devyonghee.kotlinrealworld.article.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import java.net.URI
import me.devyonghee.kotlinrealworld.article.application.ArticleService
import me.devyonghee.kotlinrealworld.article.controller.request.ArticleParameter
import me.devyonghee.kotlinrealworld.article.controller.request.ArticleRequest
import me.devyonghee.kotlinrealworld.article.controller.response.ArticleResponse
import me.devyonghee.kotlinrealworld.model.PageRequest
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleController(
    private val articleService: ArticleService,
) {

    @PostMapping("/api/articles")
    fun create(
        @RequestBody request: ArticleRequest,
        @AuthenticationPrincipal user: UserDetails
    ): ResponseEntity<ArticleResponse> {
        val response: ArticleResponse = articleService.create(user.username, request)
        return ResponseEntity.created(URI("/api/articles/${response.slug}")).body(response)
    }

    @GetMapping("/api/articles")
    fun articles(
        @AuthenticationPrincipal user: UserDetails?,
        @ModelAttribute parameters: ArticleParameter,
        @PageableDefault page: PageRequest
    ): ResponseEntity<String> {
        return ResponseEntity.ok()
            .body(mapper.writeValueAsString(articleService.articles(parameters, page, user?.username)))
    }

    companion object {
        private val mapper: ObjectMapper = ObjectMapper()
            .registerModules(JavaTimeModule())
    }
}