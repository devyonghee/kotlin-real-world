package me.devyonghee.kotlinrealworld.account.persistence.jpa

import jakarta.persistence.Entity
import jakarta.persistence.Id
import me.devyonghee.kotlinrealworld.account.domain.Account

@Entity(name = "account")
internal class AccountEntity(
    @Id
    var username: String,
    private var password: String,
) {
    constructor(account: Account) : this(
        username = account.username,
        password = account.password
    )

    fun toDomain(): Account {
        return Account(
            username = username,
            password = password,
        )
    }

    fun update(account: Account) {
        username = account.username
        password = account.password
    }
}