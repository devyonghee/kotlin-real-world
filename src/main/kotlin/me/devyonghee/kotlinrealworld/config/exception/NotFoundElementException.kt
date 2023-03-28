package me.devyonghee.kotlinrealworld.config.exception

class NotFoundElementException(
    override val message: String?,
    override val cause: Throwable? = null,
) : RealWorldException(ErrorCode.NOT_FOUND, message, cause) {

}