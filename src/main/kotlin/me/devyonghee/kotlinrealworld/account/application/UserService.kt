package me.devyonghee.kotlinrealworld.account.application

import me.devyonghee.kotlinrealworld.account.domain.Account
import me.devyonghee.kotlinrealworld.account.domain.service.AccountService
import me.devyonghee.kotlinrealworld.account.ui.request.AccountRequest
import me.devyonghee.kotlinrealworld.account.ui.request.AccountUpdateRequest
import me.devyonghee.kotlinrealworld.account.ui.request.LoginRequest
import me.devyonghee.kotlinrealworld.account.ui.response.AccountResponse
import me.devyonghee.kotlinrealworld.config.exception.NotFoundElementException
import me.devyonghee.kotlinrealworld.config.security.JsonWebTokenService
import me.devyonghee.kotlinrealworld.member.application.MemberService
import me.devyonghee.kotlinrealworld.member.domain.Member
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(
    private val accountService: AccountService,
    private val memberService: MemberService,
    private val jsonWebTokenService: JsonWebTokenService,
    private val passwordEncoder: PasswordEncoder
) {
    fun login(request: LoginRequest): AccountResponse {
        try {
            val account = accountService.account(request.email)
            if (!passwordEncoder.matches(request.password, account.password)) {
                throw IllegalArgumentException("password is not matched")
            }
            val member: Member = memberService.memberByEmail(account.email)
            return AccountResponse(
                member.email,
                jsonWebTokenService.token(member.email),
                member.username,
                member.bio,
                member.image
            )
        } catch (e: NotFoundElementException) {
            throw BadCredentialsException("bad credentials", e)
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
