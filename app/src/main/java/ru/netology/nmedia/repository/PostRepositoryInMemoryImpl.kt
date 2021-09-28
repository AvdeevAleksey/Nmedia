package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {

    private var nId = 1
    private val data = MutableLiveData(
                                listOf(
                                    Post(id = nId++,
                                        author = "Нетология. Университет интернет-профессий",
                                        published = "21 мая в 18:36",
                                        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растем сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен http://netolo.gy/fyb",
                                        likesCount = 2099999,
                                        repostsCount = 1099,
                                        viewingCount = 999999,
                                        likedByMe = false),
                                    Post(id = nId++,
                                        author = "Нетология. Университет интернет-профессий",
                                        published = "22 мая в 18:36",
                                        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растем сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен http://netolo.gy/fyb",
                                        likesCount = 999,
                                        repostsCount = 9999999,
                                        viewingCount = 999999,
                                        likedByMe = false),
                                    Post(id = nId++,
                                        author = "Нетология. Университет интернет-профессий",
                                        published = "23 мая в 18:36",
                                        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растем сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен http://netolo.gy/fyb",
                                        likesCount = 2999,
                                        repostsCount = 21999,
                                        viewingCount = 2999999,
                                        likedByMe = false)
                                ).reversed()
    )

    override fun get(): LiveData<List<Post>> = data

    override fun likeById(id: Int) {
        val posts = data.value.orEmpty().toMutableList()
        val index = posts.indexOf(posts.first{it.id == id})
        posts[index] = posts[index].copy(
            likedByMe = !posts[index].likedByMe,
            likesCount = if (!posts[index].likedByMe) posts[index].likesCount+1 else posts[index].likesCount-1
        )
        data.value = posts
    }

    override fun repostById(id: Int) {
        val posts = data.value.orEmpty().toMutableList()
        val index = posts.indexOf(posts.first{it.id == id})
        posts[index] = posts[index].copy(repostsCount = posts[index].repostsCount+1)
        data.value = posts
    }

    override fun viewingById(id: Int) {
        val posts = data.value.orEmpty().toMutableList()
        val index = posts.indexOf(posts.first{it.id == id})
        posts[index] = posts[index].copy(viewingCount = posts[index].viewingCount+1)
        data.value = posts
    }

    override fun removeById(id: Int) {
        val posts = data.value.orEmpty().toMutableList()
        data.value = posts.filter { it.id != id }
    }
}
