package ru.netology.nmedia.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import ru.netology.nmedia.databinding.ActivityPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.showMyMessage
import ru.netology.nmedia.viewmodel.PostViewModel
import java.util.*

class PostActivity : AppCompatActivity() {

    companion object {
        const val POST_KEY = "post"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPostBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val post = intent.getParcelableExtra(POST_KEY) as? Post

        with(binding.editPostContent) {
            if (post != null && !post.content.isNullOrBlank()) {
                text.append(post.content)
            } else {
                text.append("")
            }
        }

            val viewModel: PostViewModel by viewModels()

            binding.fabOk.setOnClickListener {
                with(binding.editPostContent) {

                    if (text.isNullOrBlank()) {
                        showMyMessage(ru.netology.nmedia.R.string.text_not_be_empty)
                        return@setOnClickListener
                        setResult(RESULT_CANCELED)
                    } else {
                        val intent = Intent()
                            .putExtra(POST_KEY, post?.copy(content = text.toString()))
                        setResult(RESULT_OK, intent)
                    }
                    finish()
                }
            }
        }
    }