package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun get(): LiveData<Post>
    fun likeById(id: Long)
    fun repostById(id: Long)
    fun viewingById(id: Long)
}