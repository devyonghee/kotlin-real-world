package me.devyonghee.kotlinrealworld.member.persistence

import me.devyonghee.kotlinrealworld.member.domain.Member
import me.devyonghee.kotlinrealworld.member.domain.MemberRepository
import me.devyonghee.kotlinrealworld.member.persistence.jpa.MemberEntity
import me.devyonghee.kotlinrealworld.member.persistence.jpa.MemberJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
internal class MemberDao(
    private val memberJpaRepository: MemberJpaRepository
) : MemberRepository {

    override fun save(member: Member): Member {
        return memberJpaRepository.save(MemberEntity(member)).toDomain()
    }

    override fun findByEmail(email: String): Member? {
        return memberJpaRepository.findByIdOrNull(email)?.toDomain()
    }
}