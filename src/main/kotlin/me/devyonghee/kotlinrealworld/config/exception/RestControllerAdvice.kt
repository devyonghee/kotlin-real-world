package me.devyonghee.kotlinrealworld.config.exception

import javax.naming.AuthenticationException
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class RestControllerAdvice : ResponseEntityExceptionHandler() {

    @ExceptionHandler(RealWorldException::class)
    fun handleAuthenticationException(exception: RealWorldException): ResponseEntity<ProblemDetail> {
        logger.error(exception.message, exception)
        return ResponseEntity.status(exception.httpStatus)
            .body(
                ProblemDetail.forStatusAndDetail(
                    exception.httpStatus,
                    exception.message ?: exception.localizedMessage
                ).apply {
                    title = exception.codeName
                }
            )
    }

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationException(exception: AuthenticationException): ResponseEntity<ProblemDetail> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(
                ProblemDetail.forStatusAndDetail(
                    HttpStatus.UNAUTHORIZED,
                    exception.message ?: exception.localizedMessage
                ).apply {
                    title = "UNAUTHORIZED"
                }
            )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception): ResponseEntity<ProblemDetail> {
        logger.error(exception.message, exception)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR).apply {
                    detail = exception.message ?: exception.localizedMessage
                    title = HttpStatus.INTERNAL_SERVER_ERROR.name
                }
            )
    }
}