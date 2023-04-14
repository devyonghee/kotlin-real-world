package me.devyonghee.kotlinrealworld.article.application

import java.util.UUID
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
import me.devyonghee.kotlinrealworld.tag.application.TagService
import me.devyonghee.kotlinrealworld.tag.domain.Tag
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ArticleService(
    private val articleRepository: ArticleRepository,
    private val favoriteService: FavoriteService,
    private val memberService: MemberService,
    private val followService: FollowService,
    private val tagService: TagService,
) {
    @Transactional
    fun create(email: String, request: ArticleRequest): ArticleResponse {
        val member: Member = memberService.member(email)

        val article: Article = articleRepository.save(
            Article(request.title, request.description, request.body, tagIds(request.tagList), member.username)
        )
        return articleResponse(member, article)
    }

    private fun tagIds(tagNames: List<String>): List<UUID> {
        if (tagNames.isEmpty()) {
            return emptyList()
        }
        val tagsNameToId: Map<String, UUID> = tagService.findAll(tagNames)
            .associate { it.name to it.id }
        return tagsNameToId.values + newTagIds(tagNames, tagsNameToId)
    }

    private fun newTagIds(
        tagNames: List<String>,
        tagsNameToId: Map<String, UUID>
    ): List<UUID> {
        return tagService.saveAll(tagNames.filterNot { tagsNameToId.containsKey(it) }
            .map { Tag(it) })
            .map { it.id }
    }

    fun articleResponse(member: Member, article: Article): ArticleResponse {
        val favorites: List<Favorite> = favoriteService.findAll(article.slug)
        return ArticleResponse(
            article,
            member,
            followService.exists(Follow(article.author, member.username)),
            favorites.any { member.username == it.username },
            favorites.count()
        )
    }

    fun articles(params: ArticleParams, page: Pageable) {


        articleRepository.findAll(
            ArticleRepository.ArticleFilter(
                author = params.author,
                tagId = params.tag?.let { tagService.find(it) }?.id
            ), page
        )
    }
}
