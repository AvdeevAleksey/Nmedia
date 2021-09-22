package ru.netology.nmedia.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.R
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
                likes = 999999,
                reposts = 1099,
                views = 9999,
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
                likeTextView.text = if (post.likedByMe) countMyClick(post.copy(likes = post.likes+1).likes) else countMyClick(post.copy(likes = post.likes).likes)
                likeImageView.setImageResource(
                        if (post.likedByMe) {
                            R.drawable.ic_liked_favorite_24
                        } else {
                            R.drawable.ic_baseline_favorite_border_24
                        }
                )
            }

            repostsImageView?.setOnClickListener {
                repostsTextView.text = countMyClick(post.copy(reposts = post.reposts+1).reposts)
            }

            viewsImageView?.setOnClickListener {
                viewsTextView.text = countMyClick(post.copy(views = post.views+1).views)
            }
        }
    }

    fun dischargesReduction(click: Int, t: Int = 1000): String {
        return when (click) {
            in t until t*t -> "k"
            else -> "M"
        }
    }

    fun countMyClick(click:Int, t:Int = 1000): String {
        return when (click) {
            in 1 until t -> click.toString()
            in click/t%10*t until click/t%10*t+100 -> "${click/t%10}${dischargesReduction(click)}"
            in click/t%10*t+100 until click/t%10*t+t -> "${click/t%10},${click/100-click/t%10*10}${dischargesReduction(click)}"
            in click/t%10*t until click/t%100*t+t -> "${click/t%100}${dischargesReduction(click)}"
            in click/t%100*t until click/t%1000*t+t -> "${click/t%1000}${dischargesReduction(click)}"
            else -> "${click/(t*t)}${dischargesReduction(click)}"
        }
    }
}