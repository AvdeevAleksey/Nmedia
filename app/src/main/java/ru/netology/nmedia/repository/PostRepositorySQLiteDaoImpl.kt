package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dao.AppDb
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dao.PostDaoImpl
import ru.netology.nmedia.dto.Post

class PostRepositorySQLiteDaoImpl(
    private val dao: PostDao
) : PostRepository {

    private var posts = dao.getAll()
        set(value) {
            field=value
            data.value = value
        }

    private fun sync() {

    }

    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Int) {
        data.value = posts.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                likesCount = if (it.likedByMe) it.likesCount - 1 else it.likesCount + 1
            )
        }
    }

    override fun shareById(id: Int) {
        data.value = posts.map {
            if (it.id != id) it else it.copy(
                shareCount = it.shareCount + 1
            )
        }
    }

    override fun viewingById(id: Int) {
        data.value = posts.map {
            if (it.id != id) it else it.copy(
                viewingCount = it.viewingCount + 1
            )
        }
    }

    override fun savePost(post: Post) {
        val saved = dao.savePost(post)
        data.value = if (post.id ==0) {
            listOf(saved) + posts
        } else posts.map {
            if (it.id != post.id) it else saved
        }
    }

    override fun removeById(id: Int) {
//        val daoImpl: PostDaoImpl = PostDaoImpl()
        data.value = posts.filter { it.id != id }
//        daoImpl.removeById(id)
    }
}