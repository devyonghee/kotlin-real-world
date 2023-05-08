package me.devyonghee.kotlinrealworld.config.security

import me.devyonghee.kotlinrealworld.account.domain.service.AccountService
import me.devyonghee.kotlinrealworld.member.application.MemberService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AccountUserDetailsService(
    private val accountService: AccountService,
    private val memberService: MemberService
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        return AccountUserDetails(
            accountService.account(email),
            memberService.memberByEmail(email).username
        )
    }
}