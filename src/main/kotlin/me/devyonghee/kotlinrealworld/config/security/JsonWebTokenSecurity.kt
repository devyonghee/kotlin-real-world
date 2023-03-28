package me.devyonghee.kotlinrealworld.config.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import me.devyonghee.kotlinrealworld.account.application.AccountService
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JsonWebTokenSecurity(
    private val jsonWebTokenService: JsonWebTokenService,
    private val accountService: AccountService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain
    ) {
        val authentication: String? = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (authentication.isNullOrBlank()) {
            return filterChain.doFilter(request, response)
        }
        if (!authentication.startsWith(BEARER_PREFIX)) {
            throw BadCredentialsException("Invalid token")
        }

        jsonWebTokenService.email(authentication.substring(BEARER_PREFIX.length))
            .let { email -> accountService.account(email) }
            .let { AccountUserDetails(it) }
            .also {
                SecurityContextHolder.getContext().authentication =
                    UsernamePasswordAuthenticationToken.authenticated(it, null, it.authorities)
            }
        filterChain.doFilter(request, response)
    }

    companion object {
        private const val BEARER_PREFIX = "Token "
    }
}