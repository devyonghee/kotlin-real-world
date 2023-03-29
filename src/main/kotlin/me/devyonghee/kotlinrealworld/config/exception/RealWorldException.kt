package me.devyonghee.kotlinrealworld.config.exception

import org.springframework.http.HttpStatus

open class RealWorldException(
    private val code: ErrorCode,
    override val message: String? = code.defaultMessage,
    override val cause: Throwable? = null,
) : Exception(message, cause) {

    val codeName: String = code.name
    val httpStatus: HttpStatus = code.httpStatus
}