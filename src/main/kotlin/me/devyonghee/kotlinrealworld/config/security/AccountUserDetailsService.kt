package me.devyonghee.kotlinrealworld.config.security

import me.devyonghee.kotlinrealworld.account.application.AccountService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AccountUserDetailsService(
    private val accountService: AccountService
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return AccountUserDetails(accountService.account(username))
    }
}