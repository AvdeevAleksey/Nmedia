package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Int)
    fun shareById(id: Int)
    fun viewingById(id: Int)
    fun savePost(post: Post)
    fun removeById(id: Int)
    fun openingPostById(id: Int)
}