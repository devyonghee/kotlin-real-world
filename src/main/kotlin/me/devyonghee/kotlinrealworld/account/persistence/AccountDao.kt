package me.devyonghee.kotlinrealworld.account.persistence

import me.devyonghee.kotlinrealworld.account.domain.Account
import me.devyonghee.kotlinrealworld.account.domain.AccountRepository
import me.devyonghee.kotlinrealworld.account.persistence.jpa.AccountEntity
import me.devyonghee.kotlinrealworld.account.persistence.jpa.AccountJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
internal class AccountDao(
    private val jpaRepository: AccountJpaRepository
) : AccountRepository {

    override fun save(account: Account): Account {
        return jpaRepository.save(AccountEntity(account)).toDomain()
    }

    override fun findByEmail(email: String): Account? {
        return jpaRepository.findByIdOrNull(email)?.toDomain()
    }

    override fun update(username: String, account: Account): Account {
        return jpaRepository.findByIdOrNull(username)
            ?.also { it.update(account) }
            ?.toDomain()
            ?: throw NoSuchElementException("account(username: `$username`) is not exist")
    }
}