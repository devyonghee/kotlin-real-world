package me.devyonghee.kotlinrealworld.tag.domain

interface TagRepository {

    fun saveAll(tags: List<Tag>): List<Tag>

    fun findOrNull(name: String): Tag?

    fun findByNames(names: List<String>): List<Tag>
}