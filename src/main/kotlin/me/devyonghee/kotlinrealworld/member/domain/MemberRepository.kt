package me.devyonghee.kotlinrealworld.member.domain

interface MemberRepository {

    fun exists(email: String): Boolean

    fun save(member: Member): Member

    fun findByEmail(email: String): Member?

    fun findByUsername(email: String): Member?

    fun update(email: String, member: Member): Member
}
