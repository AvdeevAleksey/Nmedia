package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post

class PostRepositorySQLiteDaoImpl(
    private val dao: PostDao
) : PostRepository {

    private var posts = dao.getAll()
        set(value) {
            field=value
            data.value = value
        }
    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Int) {
        dao.likeById(id)
    }

    override fun shareById(id: Int) {
        dao.shareById(id)
    }

    override fun viewingById(id: Int) {
        dao.viewingById(id)
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
        dao.removeById(id)
    }
}