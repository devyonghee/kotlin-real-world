package me.devyonghee.kotlinrealworld.account.application

import me.devyonghee.kotlinrealworld.account.domain.Account
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
class AccountUserCase(
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
            val member: Member = memberService.member(account.username)
            return AccountResponse(
                member.email,
                jsonWebTokenService.token(account.username),
                member.username,
                member.bio,
                member.image
            )
        } catch (e: NotFoundElementException) {
            throw BadCredentialsException("bad credentials")
        }
    }

    @Transactional
    fun register(request: AccountRequest): AccountResponse {
        val account: Account = accountService.save(Account(request.username, passwordEncoder.encode(request.password)))
        val member: Member = memberService.save(Member(request.username, request.email))

        return AccountResponse(
            member.email,
            jsonWebTokenService.token(account.username),
            member.username,
            member.bio,
            member.image
        )
    }

    fun account(username: String): AccountResponse {
        val account: Account = accountService.account(username)
        val member: Member = memberService.member(username)

        return AccountResponse(
            member.email,
            jsonWebTokenService.token(account.username),
            member.username,
            member.bio,
            member.image
        )
    }

    @Transactional
    fun update(username: String, request: AccountUpdateRequest): AccountResponse {
        val updatedAccount: Account = accountService.update(
            username, request.toAccount(
                accountService.account(username),
                passwordEncoder
            )
        )

        val updatedMember: Member = memberService.update(
            username,
            request.toMember(memberService.member(username))
        )

        return AccountResponse(
            updatedMember.email,
            jsonWebTokenService.token(updatedAccount.username),
            updatedMember.username,
            updatedMember.bio,
            updatedMember.image
        )
    }

    private fun AccountUpdateRequest.toAccount(account: Account, passwordEncoder: PasswordEncoder): Account {
        return Account(
            email ?: account.username,
            password?.let { passwordEncoder.encode(it) } ?: account.password,
        )
    }

    private fun AccountUpdateRequest.toMember(member: Member): Member {
        return Member(
            username ?: member.username,
            email ?: member.email,
            image ?: member.image,
            bio ?: member.bio
        )
    }
}
