package me.devyonghee.kotlinrealworld.member.domain

interface MemberRepository {

    fun save(member: Member): Member

    fun findByEmail(email: String): Member?
}
