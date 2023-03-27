package me.devyonghee.kotlinrealworld.member.ui

import me.devyonghee.kotlinrealworld.account.application.AccountService
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberController(
    private val accountService: AccountService,
) {

}