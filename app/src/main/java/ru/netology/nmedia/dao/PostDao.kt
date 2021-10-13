package ru.netology.nmedia.dao

import ru.netology.nmedia.dto.Post

interface PostDao {
    fun getAll(): List<Post>
    fun likeById(id: Int)
    fun shareById(id: Int)
    fun viewingById(id: Int)
    fun savePost(post: Post) :Post
    fun removeById(id: Int)
}