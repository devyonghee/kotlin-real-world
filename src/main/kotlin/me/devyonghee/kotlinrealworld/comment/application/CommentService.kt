package me.devyonghee.kotlinrealworld.comment.application

import me.devyonghee.kotlinrealworld.article.domain.Article
import me.devyonghee.kotlinrealworld.article.domain.service.ArticleService
import me.devyonghee.kotlinrealworld.comment.controller.response.CommentListResponse
import me.devyonghee.kotlinrealworld.comment.controller.response.CommentResponse
import me.devyonghee.kotlinrealworld.comment.domain.Comment
import me.devyonghee.kotlinrealworld.comment.domain.CommentRepository
import me.devyonghee.kotlinrealworld.config.exception.InvalidRequestException
import me.devyonghee.kotlinrealworld.follow.application.FollowService
import me.devyonghee.kotlinrealworld.follow.domain.Follow
import me.devyonghee.kotlinrealworld.member.application.MemberService
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val articleService: ArticleService,
    private val followService: FollowService,
    private val commentRepository: CommentRepository,
    private val memberService: MemberService,
) {

    fun create(slug: String, body: String, username: String): CommentResponse {
        if (isNotExist(username)) {
            throw InvalidRequestException("member(username: `$username`) is not exist")
        }
        val article: Article = articleService.article(slug)
        return commentResponse(
            commentRepository.save(Comment(body, username, article.id)),
            username,
        )
    }

    fun comments(articleSlug: String, username: String?): CommentListResponse {
        val article: Article = articleService.article(articleSlug)

        return CommentListResponse(
            commentRepository.findByArticleId(article.id).map {
                commentResponse(it, username)
            }
        )
    }

    fun delete(id: Long) {
        commentRepository.delete(id)
    }

    private fun commentResponse(
        comment: Comment,
        username: String?,
    ): CommentResponse {
        return CommentResponse(
            comment.id,
            comment.body,
            comment.createdAt,
            comment.updatedAt,
            memberService.member(comment.author),
            username?.let { followService.exists(Follow(it, comment.author)) } ?: false,
        )
    }

    private fun isNotExist(username: String) = memberService.exists(username)
}