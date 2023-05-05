package me.devyonghee.kotlinrealworld.account.persistence.jpa

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import me.devyonghee.kotlinrealworld.account.domain.Account

@Entity
@Table(name = "account")
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