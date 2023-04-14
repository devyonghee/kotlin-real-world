package me.devyonghee.kotlinrealworld.tag.application

import me.devyonghee.kotlinrealworld.tag.domain.Tag
import me.devyonghee.kotlinrealworld.tag.domain.TagRepository
import org.springframework.stereotype.Service

@Service
class TagService(
    private val tagRepository: TagRepository
) {

    fun saveAll(tags: List<Tag>): List<Tag> {
        return tagRepository.saveAll(tags)
    }

    fun find(name: String): Tag? {
        return tagRepository.findOrNull(name)
    }

    fun findAll(names: List<String>): List<Tag> {
        return tagRepository.findByNames(names)
    }
}