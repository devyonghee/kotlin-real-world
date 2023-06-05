package me.devyonghee.kotlinrealworld.account.application

import me.devyonghee.kotlinrealworld.account.domain.Account
import me.devyonghee.kotlinrealworld.account.domain.service.AccountService
import me.devyonghee.kotlinrealworld.account.ui.request.AccountRequest
import me.devyonghee.kotlinrealworld.account.ui.request.AccountUpdateRequest
import me.devyonghee.kotlinrealworld.account.ui.request.LoginRequest
import me.devyonghee.kotlinrealworld.account.ui.response.AccountResponse
import me.devyonghee.kotlinrealworld.config.security.JsonWebTokenService
import me.devyonghee.kotlinrealworld.member.application.MemberService
import me.devyonghee.kotlinrealworld.member.domain.Member
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(
    private val accountService: AccountService,
    private val memberService: MemberService,
    private val jsonWebTokenService: JsonWebTokenService,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager
) {
    fun login(request: LoginRequest): AccountResponse {
        return memberService.memberByEmail(request.email)
            .also {
                authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken.unauthenticated(
                        it.username,
                        request.password
                    )
                )
            }
            .let {
                AccountResponse(
                    it.email,
                    jsonWebTokenService.token(it.email),
                    it.username,
                    it.bio,
                    it.image
                )
            }
    }

    @Transactional
    fun register(request: AccountRequest): AccountResponse {
        val member: Member = memberService.save(Member(request.username, request.email))
        accountService.save(Account(request.email, passwordEncoder.encode(request.password)))

        return AccountResponse(
            member.email,
            jsonWebTokenService.token(member.email),
            member.username,
            member.bio,
            member.image
        )
    }

    fun account(username: String): AccountResponse {
        val member: Member = memberService.member(username)
        return AccountResponse(
            member.email,
            jsonWebTokenService.token(member.email),
            member.username,
            member.bio,
            member.image
        )
    }

    @Transactional
    fun update(username: String, request: AccountUpdateRequest): AccountResponse {
        val member = memberService.member(username)
        val updatedMember: Member = memberService.update(
            username,
            Member(
                request.username ?: member.username,
                request.email ?: member.email,
                request.image ?: member.image,
                request.bio ?: member.bio
            )
        )

        val account = accountService.account(updatedMember.email)
        val updatedAccount: Account = accountService.update(
            account.email, Account(
                request.email ?: account.email,
                request.password?.let { passwordEncoder.encode(it) } ?: account.password,
            )
        )

        return AccountResponse(
            updatedMember.email,
            jsonWebTokenService.token(updatedAccount.email),
            updatedMember.username,
            updatedMember.bio,
            updatedMember.image
        )
    }
}
