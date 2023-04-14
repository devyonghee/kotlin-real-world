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

    override fun saveAll(tags: List<Tag>): List<Tag> {
        return tagJpaRepository.saveAll(tags.map { TagEntity(it) }).map { it.toDomain() }
    }

    override fun findOrNull(name: String): Tag? {
        return tagJpaRepository.findByName(name)?.toDomain()
    }

    override fun findByNames(names: List<String>): List<Tag> {
        return tagJpaRepository.findAllByNameIn(names).map { it.toDomain() }
    }
}