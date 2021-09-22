package ru.netology.nmedia.dto

data class Post(
        val id: Long,
        val author: String,
        val content: String,
        val published: String,
        val likesCount: Int,
        val repostsCount: Int,
        val viewsCount: Int,
        val likedByMe: Boolean = false
)
