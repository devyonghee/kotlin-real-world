package me.devyonghee.kotlinrealworld

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.extensions.spring.SpringExtension

object KotestSpringConfiguration : AbstractProjectConfig() {
    override fun extensions() = listOf(SpringExtension)
}