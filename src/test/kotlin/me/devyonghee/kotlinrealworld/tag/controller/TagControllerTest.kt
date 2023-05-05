package me.devyonghee.kotlinrealworld.tag.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.sequences.containAllInAnyOrder
import java.util.UUID
import me.devyonghee.kotlinrealworld.DatabaseAfterEachCleanup
import me.devyonghee.kotlinrealworld.account.registerAccount
import me.devyonghee.kotlinrealworld.account.ui.response.AccountResponse
import me.devyonghee.kotlinrealworld.article.controller.request.ArticleRequest
import me.devyonghee.kotlinrealworld.article.controller.response.ArticleResponse
import me.devyonghee.kotlinrealworld.article.registerArticle
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
class TagControllerTest(
    private val mockmvc: MockMvc,
    private val jdbcTemplate: JdbcTemplate,
    private val mapper: ObjectMapper
) : StringSpec({

    listener(DatabaseAfterEachCleanup(jdbcTemplate))

    lateinit var author: AccountResponse

    beforeSpec {
        author = mockmvc.registerAccount(mapper = mapper)
    }

    "태그 목록을 조회할 수 있음" {
        // given
        val article: ArticleResponse = mockmvc.registerArticle(
            author.token,
            ArticleRequest(
                title = UUID.randomUUID().toString(),
                description = "description",
                body = "body",
                tagList = listOf("tag1", "tag2", "tag3")
            ),
            mapper = mapper
        )
        // when & then
        mockmvc.get("/api/tags")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                jsonPath("$.tags") { containAllInAnyOrder(article.tagList) }
            }
    }
})