package me.devyonghee.kotlinrealworld.tag.controller

import com.fasterxml.jackson.annotation.JsonRootName
import me.devyonghee.kotlinrealworld.tag.application.TagService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TagController(
    private val tagService: TagService
) {
    @GetMapping("/api/tags")
    fun tags(): ResponseEntity<TagsResponse> {
        return ResponseEntity.ok(tagService.tags())
    }

    @JsonRootName("tags")
    class TagsResponse(
        val tags: List<String>
    )
}