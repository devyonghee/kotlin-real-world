package me.devyonghee.kotlinrealworld.config.exception

open class InvalidRequestException(
    override val message: String?,
    override val cause: Throwable? = null,
) : RealWorldException(ErrorCode.INVALID_REQUEST, message, cause) {

}