package me.devyonghee.kotlinrealworld.account.ui.request

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import me.devyonghee.kotlinrealworld.account.ui.response.AccountResponse
import org.junit.jupiter.api.Test

class AccountRequestTest {
    @Test
    fun test() {

        val mapper = ObjectMapper()
        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);

        val readValue = mapper.readValue(
            """
            {
              "user" : {
                "email": "jake@jake.jake",
                "username": "Jacob",
                "password": "jakejake"
              }
            }
        """.trim(), AccountRequest::class.java
        )
        println(readValue)
    }

    @Test
    fun test2() {
        val mapper = ObjectMapper()
        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);

        val writeValueAsString = mapper.writeValueAsString(AccountResponse("a", "a", "a"))
        println(writeValueAsString)
    }
}