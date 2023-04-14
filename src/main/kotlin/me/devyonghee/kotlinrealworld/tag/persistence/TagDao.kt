package me.devyonghee.kotlinrealworld.tag.persistence

import me.devyonghee.kotlinrealworld.tag.domain.Tag
import me.devyonghee.kotlinrealworld.tag.domain.TagRepository
import me.devyonghee.kotlinrealworld.tag.persistence.jpa.TagEntity
import me.devyonghee.kotlinrealworld.tag.persistence.jpa.TagJpaRepository
import org.springframework.stereotype.Repository

@Repository
internal class TagDao(
    private val tagJpaRepository: TagJpaRepository
) : TagRepository {

    override fun save(tag: Tag): Tag {
        return tagJpaRepository.save(TagEntity(tag)).toDomain()
    }

    override fun findAll(name: String): List<Tag> {
        return tagJpaRepository.findAllByNameIn(listOf(name)).map { it.toDomain() }
    }
}