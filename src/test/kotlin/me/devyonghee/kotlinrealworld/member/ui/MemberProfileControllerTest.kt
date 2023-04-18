package me.devyonghee.kotlinrealworld.member.ui

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.spec.style.StringSpec
import me.devyonghee.kotlinrealworld.account.registerAccount
import me.devyonghee.kotlinrealworld.account.ui.request.AccountRequest
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
class MemberProfileControllerTest(
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
            jsonPath("$.profile.following") { value(false) }
        }
    }
})
