package me.devyonghee.kotlinrealworld.member

import org.springframework.http.HttpHeaders
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post


fun MockMvc.follow(token: String, username: String) {
    post("/api/profiles/$username/follow") {
        header(HttpHeaders.AUTHORIZATION, "Token $token")
    }
}