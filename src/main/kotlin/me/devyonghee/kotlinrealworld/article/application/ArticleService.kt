package me.devyonghee.kotlinrealworld.article.application

import me.devyonghee.kotlinrealworld.article.controller.request.ArticleParams
import me.devyonghee.kotlinrealworld.article.controller.request.ArticleRequest
import me.devyonghee.kotlinrealworld.article.controller.response.ArticleResponse
import me.devyonghee.kotlinrealworld.article.domain.Article
import me.devyonghee.kotlinrealworld.article.domain.ArticleRepository
import me.devyonghee.kotlinrealworld.favorite.application.FavoriteService
import me.devyonghee.kotlinrealworld.favorite.domain.Favorite
import me.devyonghee.kotlinrealworld.follow.application.FollowService
import me.devyonghee.kotlinrealworld.follow.domain.Follow
import me.devyonghee.kotlinrealworld.member.application.MemberService
import me.devyonghee.kotlinrealworld.member.domain.Member
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ArticleService(
    private val articleRepository: ArticleRepository,
    private val favoriteService: FavoriteService,
    private val memberService: MemberService,
    private val followService: FollowService
) {
    @Transactional
    fun create(username: String, request: ArticleRequest): ArticleResponse {
        val article: Article = articleRepository.save(
            Article(request.title, request.description, request.body, request.tagList, request.body, username)
        )
        return articleResponse(username, article)
    }

    fun articleResponse(username: String, article: Article): ArticleResponse {
        val member: Member = memberService.memberByUsername(article.author)
        val favorites: List<Favorite> = favoriteService.findAll(article.slug)
        return ArticleResponse(
            article,
            member,
            followService.exists(Follow(article.author, username)),
            favorites.any { username == it.username },
            favorites.count()
        )
    }

    fun articles(params: ArticleParams, page: Pageable) {
        TODO("Not yet implemented")
    }
}
