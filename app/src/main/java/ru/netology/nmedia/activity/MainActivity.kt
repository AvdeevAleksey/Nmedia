package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.showMyMessage
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {

    private val postRequestCode = 1

    private val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val postsAdapter = PostsAdapter (object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                val intent = Intent(Intent.ACTION_SEND)
                    .setType("text/plane")
                    .putExtra(Intent.EXTRA_TEXT,post.content)
                    .let {
                        Intent.createChooser(it,null)
                    }
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                } else {
                    showMyMessage(R.string.no_app_to_share)
                }
                viewModel.shareById(post.id)
            }

            override fun onViewing(post: Post) {
                viewModel.viewingById(post.id)
            }

            override fun onPostEdit(post: Post) {
                val intentPostEdit = Intent(this@MainActivity,PostActivity::class.java)
                    intentPostEdit.putExtra("post", post)
                startActivityForResult(intentPostEdit,postRequestCode)
            }

            override fun onPostRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onPlayVideo(post: Post) {
                Intent(Intent.ACTION_VIEW, Uri.parse(post.videoInPost)).takeIf {
                    it.resolveActivity(packageManager) != null
                }?.also {
                    startActivity(it)
                } ?: run {this@MainActivity
                    showMyMessage(R.string.no_app_to_play)
                }
            }
        })

        binding.rvPostRecyclerView.adapter = postsAdapter

        viewModel.data.observe(this) { posts ->
            postsAdapter.submitList(posts)
        }

        viewModel.edited.observe(this) { post ->
            if (post.id == 0) {
                return@observe
            }
        }

        binding.fabAddPost.setOnClickListener {
            val post: Post = viewModel.edited.value.let { post ->
               if (post?.id == 0) post else return@setOnClickListener
            }
            val intentNewPost = Intent(this@MainActivity,PostActivity::class.java)
            intentNewPost.putExtra("post", post)
            startActivityForResult(intentNewPost,postRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == postRequestCode && resultCode == RESULT_OK && data != null) {
            val post = data.getParcelableExtra<Post>(PostActivity.POST_KEY) ?: return


            viewModel.editPost(post)
            viewModel.changeContent(post.content)
            viewModel.savePost()
        }
    }
}
