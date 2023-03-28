package me.devyonghee.kotlinrealworld.account.persistence.jpa

import jakarta.persistence.Entity
import jakarta.persistence.Id
import me.devyonghee.kotlinrealworld.account.domain.Account

@Entity(name = "account")
internal class AccountEntity(
    @Id
    var email: String,
    private var password: String,
) {
    constructor(account: Account) : this(
        email = account.email,
        password = account.password
    )

    fun toDomain(): Account {
        return Account(
            email = email,
            password = password,
        )
    }
}