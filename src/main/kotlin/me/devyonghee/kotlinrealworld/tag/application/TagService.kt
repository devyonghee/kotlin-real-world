package me.devyonghee.kotlinrealworld.tag.application

import java.util.UUID
import me.devyonghee.kotlinrealworld.article.controller.response.ArticleResponse
import me.devyonghee.kotlinrealworld.tag.controller.TagController
import me.devyonghee.kotlinrealworld.tag.controller.TagController.TagsResponse
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

    fun findAllByIds(ids: Iterable<UUID>): List<Tag> {
        return tagRepository.findByIds(ids)
    }

    fun find(name: String): Tag? {
        return tagRepository.findOrNull(name)
    }

    fun findAll(names: List<String>): List<Tag> {
        return tagRepository.findByNames(names)
    }

    fun tags(): TagsResponse {
        return TagsResponse(tagRepository.findAll().map { it.name })
    }
}