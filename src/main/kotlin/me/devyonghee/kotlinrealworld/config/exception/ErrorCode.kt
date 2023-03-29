package me.devyonghee.kotlinrealworld.config.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val httpStatus: HttpStatus,
    val defaultMessage: String
) {

    NOT_FOUND(HttpStatus.NOT_FOUND, "Not Found"),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "Not Found"),
}
