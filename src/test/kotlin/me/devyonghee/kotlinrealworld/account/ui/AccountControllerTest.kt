package me.devyonghee.kotlinrealworld.account.ui

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.spec.style.StringSpec
import me.devyonghee.kotlinrealworld.account.registerAccount
import me.devyonghee.kotlinrealworld.account.ui.request.AccountRequest
import me.devyonghee.kotlinrealworld.account.ui.response.AccountResponse
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put


@AutoConfigureMockMvc(print = MockMvcPrint.SYSTEM_OUT)
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
            status { isCreated() }
            jsonPath("$.user.email") { value(email) }
            jsonPath("$.user.token") { isString() }
            jsonPath("$.user.username") { value(username) }
        }
    }

    "사용자 정보를 조회할 수 있음" {
        //given
        val email = "jake@jake.jake"
        val username = "Jacob"
        val response: AccountResponse = mockMvc.registerAccount(AccountRequest(email, "jakejake", username), mapper)
        //when & then
        mockMvc.get("/api/user") {
            header(HttpHeaders.AUTHORIZATION, "Token ${response.token}")
        }.andDo { print() }.andExpect {
            status { isOk() }
            jsonPath("$.user.email") { value(email) }
            jsonPath("$.user.token") { isString() }
            jsonPath("$.user.username") { value(username) }
        }
    }

    "사용자 정보를 수정할 수 있음" {
        //given
        val response: AccountResponse =
            mockMvc.registerAccount(AccountRequest("jake@jake.jake", "jakejake", "jake"), mapper)
        val bio = "I like to skateboard"
        val imageUri = "https://i.stack.imgur.com/xHWG8.jpg"
        //when & then
        mockMvc.put("/api/user") {
            header(HttpHeaders.AUTHORIZATION, "Token ${response.token}")
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                    "user":{
                      "email": "jake@jake.jake",
                      "bio": "$bio",
                      "image": "$imageUri"
                    }
                }
            """.trimIndent()
        }.andDo { print() }.andExpect {
            status { isOk() }
            jsonPath("$.user.bio") { value(bio) }
            jsonPath("$.user.image") { value(imageUri) }
        }
    }
})