package me.devyonghee.kotlinrealworld.account.domain

interface AccountRepository {

    fun save(account: Account): Account

    fun findByEmail(email: String): Account?

    fun update(username: String, account: Account): Account
}
