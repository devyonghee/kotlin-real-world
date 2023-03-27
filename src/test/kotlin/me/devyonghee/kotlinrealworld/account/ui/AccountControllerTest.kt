package me.devyonghee.kotlinrealworld.account.ui

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
import org.springframework.test.web.servlet.post


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
class AccountControllerTest(
    private val mockMvc: MockMvc,
    private val mapper: ObjectMapper
) : StringSpec({

    "로그인을 하면 토큰과 함께 사용자 정보를 반환" {
        //given
        val email = "jake@jake.jake"
        val password = "jakejake"
        val username = "jake"
        mockMvc.registerAccount(AccountRequest(email, password, username), mapper)
        //when & then
        mockMvc.post("/api/users/login") {
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                  "user":{
                    "email": "$email",
                    "password": "$password"
                  }
                }
            """.trimIndent()
        }.andExpect {
            status { isOk() }
            jsonPath("$.user.email") { value(email) }
            jsonPath("$.user.token") { isString() }
            jsonPath("$.user.username") { value(username) }
            jsonPath("$.user.bio") { value("I work at statefarm") }
            jsonPath("$.user.image") { exists() }
        }
    }

    "사용자를 등록할 수 있음" {
        val email = "jake@jake.jake"
        val username = "Jacob"
        mockMvc.post("/api/users") {
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                  "user":{
                    "username": "$username",
                    "email": "$email",
                    "password": "jakejake"
                  }
                }
            """.trimIndent()
        }.andExpect {
            status { isOk() }
            jsonPath("$.user.email") { value(email) }
            jsonPath("$.user.token") { isString() }
            jsonPath("$.user.username") { value(username) }
            jsonPath("$.user.bio") { value("I work at statefarm") }
            jsonPath("$.user.image") { exists() }
        }
    }
})