package me.devyonghee.kotlinrealworld.tag.domain

interface TagRepository {

    fun save(tag: Tag): Tag

    fun findAll(name: String): List<Tag>
}