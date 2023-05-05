package me.devyonghee.kotlinrealworld.comment.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.sequences.contain
import me.devyonghee.kotlinrealworld.DatabaseAfterEachCleanup
import me.devyonghee.kotlinrealworld.account.registerAccount
import me.devyonghee.kotlinrealworld.account.ui.response.AccountResponse
import me.devyonghee.kotlinrealworld.article.controller.response.ArticleResponse
import me.devyonghee.kotlinrealworld.article.registerArticle
import me.devyonghee.kotlinrealworld.comment.controller.response.CommentResponse
import me.devyonghee.kotlinrealworld.comment.registerComment
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.notNullValue
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest(
    private val mockmvc: MockMvc,
    private val jdbcTemplate: JdbcTemplate,
    private val mapper: ObjectMapper
) : StringSpec({

    lateinit var author: AccountResponse
    lateinit var article: ArticleResponse

    listener(DatabaseAfterEachCleanup(jdbcTemplate))

    beforeSpec {
        author = mockmvc.registerAccount(mapper = mapper)
        article = mockmvc.registerArticle(author.token, mapper = mapper)
    }

    "아티클에 댓글을 작성할 수 있음" {
        val body = "His name was my name too."
        // when
        mockmvc.post("/api/articles/${article.slug}/comments") {
            header(HttpHeaders.AUTHORIZATION, "Token ${author.token}")
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                    "comment": {
                        "body": "$body",
                    }
                }
            """.trimIndent()
        }.andExpect {
            status { isCreated() }
            jsonPath("$.comment.id") { notNullValue() }
            jsonPath("$.comment.body") { value(body) }
        }
    }

    "아티클의 댓글들을 조회할 수 있음" {
        // given
        val comment: CommentResponse = mockmvc.registerComment(author.token, article.slug, mapper = mapper)
        // when & then
        mockmvc.get("/api/articles/${article.slug}/comments") {
            header(HttpHeaders.AUTHORIZATION, "Token ${author.token}")
        }.andExpect {
            status { isOk() }
            jsonPath("$.comments[*].body") { contain(comment.body) }
        }
    }

    "아티클의 댓글을 삭제할 수 있음" {
        // given
        val comment: CommentResponse = mockmvc.registerComment(author.token, article.slug, mapper = mapper)
        // when
        mockmvc.delete("/api/articles/${article.slug}/comments/${comment.id}") {
            header(HttpHeaders.AUTHORIZATION, "Token ${author.token}")
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isNoContent() }
        }

        // then
        mockmvc.get("/api/articles/${article.slug}/comments") {
            header(HttpHeaders.AUTHORIZATION, "Token ${author.token}")
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            jsonPath("$.comments[*].id") { allOf(not(comment.id)) }
        }
    }
})