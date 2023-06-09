package me.devyonghee.kotlinrealworld.account.domain.service

import me.devyonghee.kotlinrealworld.account.domain.Account
import me.devyonghee.kotlinrealworld.account.domain.AccountRepository
import me.devyonghee.kotlinrealworld.config.exception.NotFoundElementException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AccountService(
    private val accountRepository: AccountRepository,
) {

    fun save(account: Account): Account {
        return accountRepository.save(account)
    }

    fun account(email: String): Account {
        return accountRepository.findByEmail(email)
            ?: throw NotFoundElementException("account(email: `$email`) is not found")
    }

    fun update(username: String, account: Account): Account {
        return accountRepository.update(username, account)
    }
}
