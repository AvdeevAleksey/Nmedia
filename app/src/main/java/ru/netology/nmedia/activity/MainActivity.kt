package ru.netology.nmedia.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.observe
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val adapter = PostsAdapter {
            viewModel.likeById(it.id)
            viewModel.repostById(it.id)
            viewModel.viewingById(it.id)
        }
        binding.rvPostRecyclerView.adapter = adapter

        viewModel.data.observe(this) { posts ->
            adapter.postsList = posts
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