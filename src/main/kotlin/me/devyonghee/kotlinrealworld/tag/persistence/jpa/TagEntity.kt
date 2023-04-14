package me.devyonghee.kotlinrealworld.tag.persistence.jpa

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.UUID
import me.devyonghee.kotlinrealworld.tag.domain.Tag

@Entity
class TagEntity(
    @Id
    var id: UUID,
    @Column(unique = true)
    var name: String,
) {
    constructor(tag: Tag) : this(
        id = tag.id,
        name = tag.name,
    )

    fun toDomain(): Tag {
        return Tag(id = id, name = name)
    }
}