package me.devyonghee.kotlinrealworld.tag.persistence.jpa

import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

internal interface TagJpaRepository : JpaRepository<TagEntity, UUID> {

    fun findByName(name: String): TagEntity?

    fun findAllByNameIn(names: List<String>): List<TagEntity>
}
