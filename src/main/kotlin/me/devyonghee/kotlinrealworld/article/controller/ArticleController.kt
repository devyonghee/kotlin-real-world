package me.devyonghee.kotlinrealworld.article.controller

import me.devyonghee.kotlinrealworld.article.application.ArticleService
import me.devyonghee.kotlinrealworld.article.controller.request.ArticleParams
import me.devyonghee.kotlinrealworld.article.controller.request.ArticleRequest
import me.devyonghee.kotlinrealworld.article.controller.response.ArticleResponse
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URI

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
    fun articles(@RequestParam params: ArticleParams, @PageableDefault page: Pageable) {
        return articleService.articles(params, page)
    }
}