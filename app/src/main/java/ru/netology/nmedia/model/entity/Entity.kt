package ru.netology.nmedia.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val videoInPost: String,
    val likesCount: Int,
    val shareCount: Int,
    val viewingCount: Int,
    val likedByMe: Boolean = false
)