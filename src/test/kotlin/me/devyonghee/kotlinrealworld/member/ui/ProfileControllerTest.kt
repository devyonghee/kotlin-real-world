package me.devyonghee.kotlinrealworld.member.ui

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.spec.style.StringSpec
import me.devyonghee.kotlinrealworld.account.registerAccount
import me.devyonghee.kotlinrealworld.account.ui.request.AccountRequest
import me.devyonghee.kotlinrealworld.account.ui.response.AccountResponse
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProfileControllerTest(
    private val mockMvc: MockMvc,
    private val mapper: ObjectMapper,
) : StringSpec({

    "다른 사용자의 프로필을 조회할 수 있음" {
        //given
        val email = "jake@jake.jake"
        val username = "Jacob"
        mockMvc.registerAccount(AccountRequest(email, "jakejake", username), mapper)
        //when & then
        mockMvc.get("/api/profiles/$username") {
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            jsonPath("$.profile.username") { value(username) }
            jsonPath("$.profile.email") { value(email) }
            jsonPath("$.profile.following") { value(false) }
        }
    }

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
        }.andExpect {
            status { isOk() }
            jsonPath("$.profile.username") { value(jake) }
            jsonPath("$.profile.email") { value(jakeEmail) }
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
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            jsonPath("$.profile.username") { value(jake) }
            jsonPath("$.profile.email") { value(jakeEmail) }
            jsonPath("$.profile.following") { value(false) }
        }
    }
})
