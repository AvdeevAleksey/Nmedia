package ru.netology.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.model.dto.Post
import ru.netology.nmedia.utils.AndroidUtils
import java.util.*

class PostRepositoryFile(
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
            data.value = value
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
            return
        }
        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Int) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                likesCount = if (it.likedByMe) it.likesCount - 1 else it.likesCount + 1
            )
        }
    }

    override fun shareById(id: Int) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                shareCount = it.shareCount + 1
            )
        }
    }

    override fun viewingById(id: Int) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                viewingCount = it.viewingCount + 1
            )
        }
    }

    override fun removeById(id: Int) {
        posts = posts.filter { it.id != id }
    }

    private fun sync() {
        file.writeText(gson.toJson(posts))
    }
}
