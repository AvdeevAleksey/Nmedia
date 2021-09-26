package ru.netology.nmedia.repository

import android.util.MutableDouble
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {

    private val posts = mutableListOf(
        Post(id = 1,
            author = "Нетология. Университет интернет-профессий",
            published = "21 мая в 18:36",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растем сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен http://netolo.gy/fyb",
            likesCount = 2099999,
            repostsCount = 1099,
            viewsCount = 999999,
            likedByMe = false),
        Post(id = 2,
            author = "Нетология. Университет интернет-профессий",
            published = "22 мая в 18:36",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растем сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен http://netolo.gy/fyb",
            likesCount = 999,
            repostsCount = 9999999,
            viewsCount = 999999,
            likedByMe = false),
        Post(id = 3,
            author = "Нетология. Университет интернет-профессий",
            published = "23 мая в 18:36",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растем сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен http://netolo.gy/fyb",
            likesCount = 2999,
            repostsCount = 21999,
            viewsCount = 2999999,
            likedByMe = false))

    private val data = MutableLiveData(posts)

    override fun get(): LiveData<MutableList<Post>> = data

    override fun likeById(id: Int) {
        val nid: Int = id - 1
        posts[nid] = posts[nid].copy(likedByMe = !posts[nid].likedByMe,likesCount = if (!posts[nid].likedByMe) posts[nid].likesCount+1 else posts[nid].likesCount-1)
        data.value = posts
    }

    override fun repostById(id: Int) {
        val nid: Int = id - 1
        posts[nid] = posts[nid].copy(repostsCount = posts[nid].repostsCount+1)
        data.value = posts
    }

    override fun viewingById(id: Int) {
        val nid: Int = id - 1
        posts[nid] = posts[nid].copy(viewsCount = posts[nid].viewsCount+1)
        data.value = posts
    }
}
