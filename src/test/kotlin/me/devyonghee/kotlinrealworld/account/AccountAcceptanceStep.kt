package me.devyonghee.kotlinrealworld.account

import com.fasterxml.jackson.databind.ObjectMapper
import me.devyonghee.kotlinrealworld.account.ui.request.AccountRequest
import me.devyonghee.kotlinrealworld.account.ui.response.AccountResponse
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

var number: Int = 0

fun MockMvc.registerAccount(
    request: AccountRequest = AccountRequest("email${++number}@email.com", "password", "user$number"),
    mapper: ObjectMapper
): AccountResponse {
    return post("/api/users") {
        contentType = MediaType.APPLICATION_JSON
        content = mapper.writeValueAsString(request)
    }.andReturn()
        .response
        .contentAsString
        .let { mapper.readValue(it, AccountResponse::class.java) }
}