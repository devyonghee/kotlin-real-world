package me.devyonghee.kotlinrealworld.article.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository

interface ArticleJpaRepository : JpaRepository<ArticleEntity, String> {
}