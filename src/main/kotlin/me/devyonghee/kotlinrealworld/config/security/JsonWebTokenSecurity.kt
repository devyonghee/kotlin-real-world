package me.devyonghee.kotlinrealworld.config.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import me.devyonghee.kotlinrealworld.account.domain.service.AccountService
import me.devyonghee.kotlinrealworld.member.application.MemberService
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JsonWebTokenSecurity(
    private val jsonWebTokenService: JsonWebTokenService,
    private val accountService: AccountService,
    private val memberService: MemberService
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
            .let { User(memberService.memberByEmail(it.email).username, "", listOf()) }
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