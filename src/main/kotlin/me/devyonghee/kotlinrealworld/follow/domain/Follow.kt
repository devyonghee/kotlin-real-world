package me.devyonghee.kotlinrealworld.follow.domain

data class Follow(
    val followerUsername: String,
    val followeeUsername: String,
    val id: Long? = 0,
) {
}