package me.devyonghee.kotlinrealworld.config.security

import me.devyonghee.kotlinrealworld.account.domain.service.AccountService
import me.devyonghee.kotlinrealworld.member.application.MemberService
import me.devyonghee.kotlinrealworld.member.domain.Member
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AccountUserDetailsService(
    private val accountService: AccountService,
    private val memberService: MemberService
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val member: Member = memberService.member(username)
        return User(
            member.username,
            accountService.account(member.email).password,
            listOf()
        )
    }
}