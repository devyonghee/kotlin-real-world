package me.devyonghee.kotlinrealworld.tag.domain

import java.util.UUID

interface TagRepository {
    fun findByIds(ids: Iterable<UUID>): List<Tag>

    fun saveAll(tags: List<Tag>): List<Tag>

    fun findOrNull(name: String): Tag?

    fun findByNames(names: List<String>): List<Tag>

    fun findAll(): List<Tag>
}