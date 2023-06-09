package me.devyonghee.kotlinrealworld.comment.controller

import me.devyonghee.kotlinrealworld.comment.application.CommentService
import me.devyonghee.kotlinrealworld.comment.controller.request.CommentRequest
import me.devyonghee.kotlinrealworld.comment.controller.response.CommentListResponse
import me.devyonghee.kotlinrealworld.comment.controller.response.CommentResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CommentController(
    private val service: CommentService
) {

    @PostMapping("/api/articles/{slug}/comments")
    fun create(
        @PathVariable slug: String,
        @RequestBody @Validated request: CommentRequest,
        @AuthenticationPrincipal userDetail: UserDetails
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity.ok(service.create(slug, request.body, userDetail.username))
    }


    @GetMapping("/api/articles/{slug}/comments")
    fun comments(
        @PathVariable slug: String,
        @AuthenticationPrincipal userDetail: UserDetails?
    ): ResponseEntity<CommentListResponse> {
        return ResponseEntity.ok(service.comments(slug, userDetail?.username))
    }

    @DeleteMapping("/api/articles/{slug}/comments/{id}")
    @PreAuthorize("@commentOwnerAuthenticator.isOwner(#id, #user.username)")
    fun delete(
        @PathVariable slug: String,
        @PathVariable id: Long,
        @AuthenticationPrincipal user: UserDetails
    ): ResponseEntity<Unit> {
        service.delete(id)
        return ResponseEntity.noContent().build()
    }
}