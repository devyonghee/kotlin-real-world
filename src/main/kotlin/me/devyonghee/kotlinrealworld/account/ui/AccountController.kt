package me.devyonghee.kotlinrealworld.account.ui

import me.devyonghee.kotlinrealworld.account.application.AccountUserCase
import me.devyonghee.kotlinrealworld.account.ui.request.AccountRequest
import me.devyonghee.kotlinrealworld.account.ui.request.AccountUpdateRequest
import me.devyonghee.kotlinrealworld.account.ui.request.LoginRequest
import me.devyonghee.kotlinrealworld.account.ui.response.AccountResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
class AccountController(
    private val accountUserCase: AccountUserCase,
) {
    @PostMapping("/api/users/login")
    fun login(@Validated @RequestBody request: LoginRequest): ResponseEntity<AccountResponse> {
        return ResponseEntity.ok(accountUserCase.login(request))
    }

    @PostMapping("/api/users")
    fun register(@Validated @RequestBody request: AccountRequest): ResponseEntity<AccountResponse> {
        return ResponseEntity.created(URI("/api/user"))
            .body(accountUserCase.register(request))
    }

    @GetMapping("/api/user")
    fun account(@AuthenticationPrincipal user: UserDetails): ResponseEntity<AccountResponse> {
        return ResponseEntity.ok(accountUserCase.account(user.username))
    }

    @PutMapping("/api/user")
    fun update(
        @AuthenticationPrincipal user: UserDetails,
        @Validated @RequestBody request: AccountUpdateRequest
    ): ResponseEntity<AccountResponse> {
        return ResponseEntity.ok(accountUserCase.update(user.username, request))
    }
}