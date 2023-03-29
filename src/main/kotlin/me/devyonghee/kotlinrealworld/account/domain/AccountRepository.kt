package me.devyonghee.kotlinrealworld.account.domain

interface AccountRepository {

    fun save(account: Account): Account

    fun findByEmail(email: String): Account?

    fun update(email: String, account: Account): Account
}
