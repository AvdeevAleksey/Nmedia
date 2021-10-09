package ru.netology.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.AndroidUtils
import java.util.*

class PostRepositoryInMemoryImpl(
    context: Context
) : PostRepository {

    private val file = context.filesDir.resolve(AndroidUtils.POST_FILE)
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val gson = Gson()
    private var posts : List<Post> = file.exists().let { exists ->
        if (exists) {
            gson.fromJson(file.readText(), type)
        } else emptyList()
    }
        set(value) {
            field=value
            sync()
        }

    private var nId = posts.maxByOrNull {
            it.id
        }?.id?: 0

    private val data = MutableLiveData(posts)

    override fun savePost(post: Post) {
        if (post.id == 0) {
            posts = listOf(
                post.copy(
                    id = ++nId,
                    author = "Me",
                    likedByMe = false,
                    published = Calendar.getInstance().time.toString()
                )
            ) + posts
            data.value = posts
            return
        }
        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
        data.value = posts
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Int) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                likesCount = if (it.likedByMe) it.likesCount - 1 else it.likesCount + 1
            )
        }
        data.value = posts
    }

    override fun shareById(id: Int) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                shareCount = it.shareCount + 1
            )
        }
        data.value = posts
    }

    override fun viewingById(id: Int) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                viewingCount = it.viewingCount + 1
            )
        }
        data.value = posts
    }

    override fun removeById(id: Int) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    private fun sync() {
        file.writeText(gson.toJson(posts))
    }
}
