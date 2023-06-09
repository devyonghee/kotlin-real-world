package me.devyonghee.kotlinrealworld.member.persistence.jpa

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import me.devyonghee.kotlinrealworld.member.domain.Member

@Entity
@Table(name = "member")
class MemberEntity(
    @Id
    var username: String,
    var email: String,
    private var bio: String?,
    private var image: String?
) {

    constructor(member: Member) : this(
        username = member.username,
        email = member.email,
        bio = member.bio,
        image = member.image
    )

    fun toDomain(): Member {
        return Member(
            username = username,
            email = email,
            bio = bio,
            image = image
        )
    }

    fun update(member: Member) {
        email = member.email
        username = member.username
        bio = member.bio
        image = member.image
    }
}
