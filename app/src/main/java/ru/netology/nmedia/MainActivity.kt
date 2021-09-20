package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
                id = 1,
                author = "Нетология. Университет интернет-профессий",
                published = "21 мая в 18:36",
                content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растем сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен http://netolo.gy/fyb",
                likes = 999,
                reposts = 999,
                views = 1099,
                likedByMe = false
        )
        with(binding) {
            authorTextView.text = post.author
            publishedTextView.text = post.published
            contentTextView.text = post.content
            likeTextView.text = countMyClick(post.likes)
            repostsTextView.text = countMyClick(post.reposts)
            viewsTextView.text = countMyClick(post.views)

//            if (post.likedByMe) likeImageView?.setImageResource(R.drawable.ic_liked_favorite_24)

            likeImageView?.setOnClickListener {
                post.likedByMe = !post.likedByMe
                likeTextView.text = if (post.likedByMe) countMyClick(post.likes+1) else countMyClick(post.likes-1)
                likeImageView.setImageResource(
                        if (post.likedByMe) {
                            R.drawable.ic_liked_favorite_24
                        } else {
                            R.drawable.ic_baseline_favorite_border_24
                        }
                )
            }

            var rePostCount:Int = post.reposts
            var viewsCount:Int = post.views

            repostsImageView?.setOnClickListener {
                repostsTextView.text = countMyClick(++rePostCount)
            }

            viewsImageView?.setOnClickListener {
                viewsTextView.text = countMyClick(++viewsCount)
            }
        }
    }

    fun dischargesReduction(click: Int, thousand: Int = 1000): String {
        return when (click) {
            in thousand until thousand*thousand -> "k"
            else -> "M"
        }
    }

    fun countMyClick(click:Int, thousand:Int = 1000): String {
        return when (click) {
            in 1 until thousand -> click.toString()
            in thousand until thousand+99 -> "1${dischargesReduction(click)}"
            in thousand until thousand*10 -> "${click/thousand%10},${(click/thousand-click/thousand%10)*10}${dischargesReduction(click)}"
            in thousand*10 until thousand*thousand -> "${click/thousand%10}${dischargesReduction(click)}"
            else -> click.toString()
        }
    }
}