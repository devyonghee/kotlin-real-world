package me.devyonghee.kotlinrealworld.tag.domain

import java.util.UUID

data class Tag(
    val name: String,
    val id: UUID = UUID.randomUUID(),
) {
    init {
        require(name.isNotBlank()) { "'name' must not be blank" }
    }
}
