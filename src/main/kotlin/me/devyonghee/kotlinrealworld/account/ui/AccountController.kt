package me.devyonghee.kotlinrealworld.account.ui

import me.devyonghee.kotlinrealworld.account.application.AccountService
import org.springframework.web.bind.annotation.RestController

@RestController
class AccountController(
    private val accountService: AccountService,
) {

}