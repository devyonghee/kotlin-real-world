package me.devyonghee.kotlinrealworld.member.application

import me.devyonghee.kotlinrealworld.config.exception.NotFoundElementException
import me.devyonghee.kotlinrealworld.member.domain.Member
import me.devyonghee.kotlinrealworld.member.domain.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberService(
    private val memberRepository: MemberRepository
) {

    fun member(email: String): Member {
        return memberRepository.findByEmail(email)
            ?: throw NotFoundElementException("member(email: `$email`) is not exist")
    }

    fun save(member: Member): Member {
        return memberRepository.save(member)
    }
}
