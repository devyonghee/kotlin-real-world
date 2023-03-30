package me.devyonghee.kotlinrealworld.article.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.spec.style.StringSpec
import me.devyonghee.kotlinrealworld.account.registerAccount
import me.devyonghee.kotlinrealworld.account.ui.request.AccountRequest
import me.devyonghee.kotlinrealworld.account.ui.response.AccountResponse
import org.hamcrest.Matchers.notNullValue
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
class ArticleControllerTest(
    private val mockmvc: MockMvc,
    private val objectMapper: ObjectMapper
) : StringSpec({

    "아티클을 작성할 수 있음" {
        //given
        val account: AccountResponse =
            mockmvc.registerAccount(AccountRequest("author@author.com", "password", "author"), objectMapper)
        //when
        mockmvc.post("/api/articles") {
            header(HttpHeaders.AUTHORIZATION, "Token ${account.token}")
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
            jsonPath("$.article.author.username") { value("author") }
        }
    }


    "아티클 리스트를 조회할 수 있음" {
        val account: AccountResponse =
            mockmvc.registerAccount(AccountRequest("author@author.com", "password", "author"), objectMapper)


        mockmvc.get("/api/articles") {
            param("tag", "tag")
            param("author", "author")
            param("favorited", "favorited")
            param("limit", "10")
            param("offset", "0")

        }.andExpect {
            status { isOk() }
        }
    }
})