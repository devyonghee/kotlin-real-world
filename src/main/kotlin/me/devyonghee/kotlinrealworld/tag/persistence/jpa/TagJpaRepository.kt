package me.devyonghee.kotlinrealworld.tag.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository

internal interface TagJpaRepository : JpaRepository<TagEntity, String> {

    fun findByName(name: String): TagEntity?
    fun findAllByNameIn(names: List<String>): List<TagEntity>
}
