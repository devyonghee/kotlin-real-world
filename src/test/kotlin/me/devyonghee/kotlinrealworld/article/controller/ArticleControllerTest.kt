package me.devyonghee.kotlinrealworld.article.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.spec.style.StringSpec
import me.devyonghee.kotlinrealworld.account.registerAccount
import me.devyonghee.kotlinrealworld.account.ui.request.AccountRequest
import me.devyonghee.kotlinrealworld.account.ui.response.AccountResponse
import me.devyonghee.kotlinrealworld.article.controller.response.ArticleResponse
import me.devyonghee.kotlinrealworld.article.registerArticle
import me.devyonghee.kotlinrealworld.member.follow
import org.hamcrest.Matchers.notNullValue
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put

@SpringBootTest
@AutoConfigureMockMvc
class ArticleControllerTest(
    private val mockmvc: MockMvc,
    private val mapper: ObjectMapper
) : StringSpec({

    lateinit var author: AccountResponse

    beforeSpec {
        author = mockmvc.registerAccount(AccountRequest("author@author.com", "password", "author"), mapper)
    }

    "아티클을 작성할 수 있음" {
        // when
        mockmvc.post("/api/articles") {
            header(HttpHeaders.AUTHORIZATION, "Token ${author.token}")
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                    "article": {
                        "title": "title",
                        "description": "description",
                        "body": "body",
                        "tagList": ["tag1", "tag2"]
                    }
                }
            """.trimIndent()
        }.andExpect {
            status { isCreated() }
            jsonPath("$.article.slug") { value("title") }
            jsonPath("$.article.title") { value("title") }
            jsonPath("$.article.createdAt") { notNullValue() }
            jsonPath("$.article.updatedAt") { notNullValue() }
            jsonPath("$.article.favorited") { value(false) }
            jsonPath("$.article.favoritesCount") { value(0) }
            jsonPath("$.article.author.username") { value(author.username) }
        }
    }


    "아티클 리스트를 조회할 수 있음" {
        // given
        val article: ArticleResponse = mockmvc.registerArticle(author.token, mapper = mapper)
        // when & then
        mockmvc.get("/api/articles") {
            header(HttpHeaders.AUTHORIZATION, "Token ${author.token}")
            param("tag", article.tagList[0])
            param("author", article.author.username)
            param("limit", "5")
            param("offset", "0")

        }.andDo { print() }.andExpect {
            status { isOk() }
            jsonPath("$.articles[0].slug") { value(article.slug) }
            jsonPath("$.articles[0].title") { value(article.title) }
            jsonPath("$.articles[0].description") { value(article.description) }
            jsonPath("$.articles[0].author.username") { value(article.author.username) }
        }
    }

    "팔로우한 사용자의 아티클을 조회할 수 있음" {
        // given
        mockmvc.registerArticle(author.token, mapper = mapper)
        val anyAccount: AccountResponse =
            mockmvc.registerAccount(AccountRequest("any@any.com", "password", "any"), mapper)
        mockmvc.follow(anyAccount.token, author.username)

        // when & then
        mockmvc.get("/api/articles/feed") {
            header(HttpHeaders.AUTHORIZATION, "Token ${anyAccount.token}")
            param("limit", "10")
            param("offset", "0")
        }.andExpect {
            status { isOk() }
            jsonPath("$.articles") { isArray() }
        }
    }

    "아티클을 수정할 수 있음" {
        // given
        val article: ArticleResponse =
            mockmvc.registerArticle(author.token, mapper = mapper)
        val newTitle = "newTitle"

        // when & then
        mockmvc.put("/api/articles/${article.slug}") {
            header(HttpHeaders.AUTHORIZATION, "Token ${author.token}")
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                    "article": {
                        "title": "$newTitle"
                    }
                }
            """.trimIndent()
        }.andExpect {
            status { isOk() }
            jsonPath("$.article.title") { value(newTitle) }
        }
    }

    "아티클을 삭제할 수 있음" {
        // given
        val article: ArticleResponse = mockmvc.registerArticle(author.token, mapper = mapper)

        // when
        mockmvc.delete("/api/articles/{slug}", article.slug) {
            header(HttpHeaders.AUTHORIZATION, "Token ${author.token}")
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isNoContent() }
        }

        // then
        mockmvc.get("/api/articles/{slug}", article.slug) {
            header(HttpHeaders.AUTHORIZATION, "Token ${author.token}")
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isNotFound() }
        }
    }
})