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

    override fun exists(email: String): Boolean {
        return memberJpaRepository.existsById(email)
    }

    override fun save(member: Member): Member {
        return memberJpaRepository.save(MemberEntity(member)).toDomain()
    }

    override fun findByEmail(email: String): Member? {
        return memberJpaRepository.findByEmail(email)?.toDomain()
    }

    override fun findByUsername(username: String): Member? {
        return memberJpaRepository.findByIdOrNull(username)?.toDomain()
    }

    override fun update(email: String, member: Member): Member {
        return memberJpaRepository.findByIdOrNull(email)
            ?.also { it.update(member) }
            ?.toDomain()
            ?: throw NoSuchElementException("member(email: `$email`) is not exist")
    }
}