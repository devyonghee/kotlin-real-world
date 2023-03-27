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
import org.springframework.test.web.servlet.get

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MemberControllerTest(
    private val mockMvc: MockMvc,
    private val mapper: ObjectMapper,
) : StringSpec({

    "사용자 정보를 조회할 수 있음" {
        //given
        val email = "jake@jake.jake"
        val username = "Jacob"
        val response: AccountResponse = mockMvc.registerAccount(AccountRequest(email, "jakejake", username), mapper)
        //when & then
        mockMvc.get("/api/user") {
            header(HttpHeaders.AUTHORIZATION, "Token ${response.token}")
        }.andExpect {
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
        mockMvc.get("/api/user") {
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
        }.andExpect {
            status { isOk() }
            jsonPath("$.user.bio") { value(bio) }
            jsonPath("$.user.image") { value(imageUri) }
        }
    }
})
