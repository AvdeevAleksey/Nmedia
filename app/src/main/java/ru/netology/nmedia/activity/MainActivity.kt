package ru.netology.nmedia.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.observe
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val postsAdapter = PostsAdapter (
            onLikeListener = {viewModel.likeById(it.id)},
            onShareListener = {viewModel.repostById(it.id)},
            onViewingListener = {viewModel.viewingById(it.id)}
        )
        binding.rvPostRecyclerView.adapter = postsAdapter

        viewModel.data.observe(this) { posts ->
            postsAdapter.submitList(posts)
        }
    }
}

