package me.devyonghee.kotlinrealworld.article.controller

import java.net.URI
import me.devyonghee.kotlinrealworld.article.application.MemberArticleService
import me.devyonghee.kotlinrealworld.article.controller.request.ArticleParameter
import me.devyonghee.kotlinrealworld.article.controller.request.ArticleRequest
import me.devyonghee.kotlinrealworld.article.controller.request.ArticleUpdateRequest
import me.devyonghee.kotlinrealworld.article.controller.response.ArticleListResponse
import me.devyonghee.kotlinrealworld.article.controller.response.ArticleResponse
import me.devyonghee.kotlinrealworld.model.PageRequest
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleController(
    private val memberArticleService: MemberArticleService,
) {

    @PostMapping("/api/articles")
    fun create(
        @RequestBody request: ArticleRequest,
        @AuthenticationPrincipal user: UserDetails
    ): ResponseEntity<ArticleResponse> {
        val response: ArticleResponse = memberArticleService.create(user.username, request)
        return ResponseEntity.created(URI("/api/articles/${response.slug}")).body(response)
    }

    @GetMapping("/api/articles")
    fun articles(
        @AuthenticationPrincipal user: UserDetails?,
        @ModelAttribute parameters: ArticleParameter,
        @PageableDefault page: PageRequest
    ): ResponseEntity<ArticleListResponse> {
        return ResponseEntity.ok()
            .body(memberArticleService.articles(parameters, page, user?.username))
    }

    @GetMapping("/api/articles/{slug}")
    fun article(
        @PathVariable slug: String,
        @AuthenticationPrincipal user: UserDetails?
    ): ResponseEntity<ArticleResponse> {
        return ResponseEntity.ok()
            .body(memberArticleService.article(slug, user?.username))
    }

    @GetMapping("/api/articles/feed")
    fun feedArticles(
        @AuthenticationPrincipal user: UserDetails,
        @PageableDefault page: PageRequest
    ): ResponseEntity<ArticleListResponse> {
        return ResponseEntity.ok()
            .body(memberArticleService.feedArticles(user.username, page))
    }

    @PutMapping("/api/articles/{slug}")
    fun updateArticle(
        @AuthenticationPrincipal user: UserDetails,
        @PathVariable slug: String,
        @RequestBody request: ArticleUpdateRequest
    ): ResponseEntity<ArticleResponse> {
        return ResponseEntity.ok()
            .body(memberArticleService.changeArticle(user.username, slug, request))
    }

    @DeleteMapping("/api/articles/{slug}")
    @PreAuthorize("@articleOwnerAuthenticator.isOwner(#slug, #user.username)")
    fun deleteArticle(
        @AuthenticationPrincipal user: UserDetails,
        @PathVariable slug: String,
    ): ResponseEntity<Unit> {
        memberArticleService.deleteArticle(slug)
        return ResponseEntity.noContent().build()
    }
}