package ru.netology.nmedia.repository

import android.util.MutableDouble
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {

    private var posts = Post(
        id = 1,
        author = "Нетология. Университет интернет-профессий",
        published = "21 мая в 18:36",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растем сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен http://netolo.gy/fyb",
        likesCount = 999,
        repostsCount = 1099,
        viewsCount = 999999,
        likedByMe = false
    )

    private val data = MutableLiveData(posts)

    override fun get(): LiveData<Post> = data

    override fun like() {
        posts = posts.copy(likedByMe = !posts.likedByMe,likesCount = if (!posts.likedByMe) posts.likesCount+1 else posts.likesCount-1)
        data.value = posts
    }

    override fun repost() {
        posts = posts.copy(repostsCount = posts.repostsCount+1)
        data.value = posts
    }

    override fun view() {
        posts = posts.copy(viewsCount = posts.viewsCount+1)
        data.value = posts
    }
}
