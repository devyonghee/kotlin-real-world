package me.devyonghee.kotlinrealworld.account.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository

internal interface AccountJpaRepository : JpaRepository<AccountEntity, String> {

}