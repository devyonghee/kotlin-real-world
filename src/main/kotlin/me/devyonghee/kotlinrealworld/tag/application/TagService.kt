package me.devyonghee.kotlinrealworld.tag.application

import me.devyonghee.kotlinrealworld.tag.domain.Tag
import me.devyonghee.kotlinrealworld.tag.domain.TagRepository
import org.springframework.stereotype.Service

@Service
class TagService(
    private val tagRepository: TagRepository
) {

    fun save(tag: String): Tag {
        return tagRepository.save(Tag(tag))
    }

    fun findAll(name: String): List<Tag> {
        return tagRepository.findAll(name)
    }
}