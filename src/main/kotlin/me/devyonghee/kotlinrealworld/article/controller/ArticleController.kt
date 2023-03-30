package me.devyonghee.kotlinrealworld.article.controller

import me.devyonghee.kotlinrealworld.article.controller.request.ArticleParams
import me.devyonghee.kotlinrealworld.article.controller.request.ArticleRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleController {

    @GetMapping("/api/articles")
    fun articles(@RequestParam params: ArticleParams, @PageableDefault page: Pageable) {
        // TODO
    }

    @PostMapping("/api/articles")
    fun create(@RequestBody request: ArticleRequest, @AuthenticationPrincipal user: UserDetails) {
        // TODO
    }
}