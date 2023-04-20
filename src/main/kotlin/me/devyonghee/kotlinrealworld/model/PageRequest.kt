package me.devyonghee.kotlinrealworld.model

import org.springframework.data.domain.AbstractPageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

data class PageRequest(
    val limit: Int = 20,
    val offset: Int = 0,
) : AbstractPageRequest(offset, limit) {

    override fun getSort(): Sort {
        return Sort.unsorted()
    }

    override fun next(): Pageable {
        return PageRequest(limit, offset + limit)
    }

    override fun previous(): Pageable {
        return PageRequest(limit, offset - limit)
    }

    override fun first(): Pageable {
        return PageRequest(limit, 0)
    }

    override fun withPage(pageNumber: Int): Pageable {
        return PageRequest(limit, pageNumber)
    }
}