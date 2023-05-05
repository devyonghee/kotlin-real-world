package me.devyonghee.kotlinrealworld.member.ui

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.spec.style.StringSpec
import me.devyonghee.kotlinrealworld.DatabaseAfterEachCleanup
import me.devyonghee.kotlinrealworld.account.registerAccount
import me.devyonghee.kotlinrealworld.account.ui.request.AccountRequest
import me.devyonghee.kotlinrealworld.account.ui.response.AccountResponse
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.post

@AutoConfigureMockMvc
@SpringBootTest
class MemberFollowControllerTest(
    private val mockMvc: MockMvc,
    private val jdbcTemplate: JdbcTemplate,
    private val mapper: ObjectMapper,
) : StringSpec({

    listener(DatabaseAfterEachCleanup(jdbcTemplate))

    "다른 사용자를 팔로우 할 수 있음" {
        //given
        val jake = "jake"
        val jakeEmail = "jake@jake.jake"
        mockMvc.registerAccount(AccountRequest(jakeEmail, "jakejake", jake), mapper)
        val yongAccount: AccountResponse =
            mockMvc.registerAccount(AccountRequest("yong@yong.yong", "yong", "yong"), mapper)
        //when & then
        mockMvc.post("/api/profiles/$jake/follow") {
            header(HttpHeaders.AUTHORIZATION, "Token ${yongAccount.token}")
        }.andDo { print() }.andExpect {
            status { isOk() }
            jsonPath("$.profile.username") { value(jake) }
            jsonPath("$.profile.following") { value(true) }
        }
    }

    "다른 사용자를 언팔로우 할 수 있음" {
        //given
        val jake = "jake"
        val jakeEmail = "jake@jake.jake"
        mockMvc.registerAccount(AccountRequest(jakeEmail, "jakejake", jake), mapper)
        val yongAccount: AccountResponse =
            mockMvc.registerAccount(AccountRequest("yong@yong.yong", "yong", "yong"), mapper)

        mockMvc.post("/api/profiles/$jake/follow") {
            header(HttpHeaders.AUTHORIZATION, "Token ${yongAccount.token}")
        }
        //when & then
        mockMvc.delete("/api/profiles/$jake/follow") {
            header(HttpHeaders.AUTHORIZATION, "Token ${yongAccount.token}")
        }.andExpect {
            status { isOk() }
            jsonPath("$.profile.username") { value(jake) }
            jsonPath("$.profile.following") { value(false) }
        }
    }
})
