package me.devyonghee.kotlinrealworld.member.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository

internal interface MemberJpaRepository : JpaRepository<MemberEntity, String> {

    fun findByEmail(email: String): MemberEntity?
}
