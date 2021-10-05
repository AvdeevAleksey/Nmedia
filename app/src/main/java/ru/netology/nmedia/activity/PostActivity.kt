package ru.netology.nmedia.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.ActivityPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.AndroidUtils
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

        val viewModel: PostViewModel by viewModels()

        binding.fabOk.setOnClickListener{
            with(binding.editPostContent) {

                setText(intent?.let {
                    it.getStringExtra("content")
                })

                if (text.isNullOrBlank()) {
                    showMyMessage(ru.netology.nmedia.R.string.text_not_be_empty)
                    return@setOnClickListener
                    setResult(RESULT_CANCELED)
                } else {
                    val intent = Intent()
                        .putExtra(POST_KEY, text)
                    viewModel.changeContent(text.toString())
                    viewModel.savePost()
                    setResult(RESULT_OK)
                    clearFocus()
                    AndroidUtils.hideKeyboard(this)
                }
            }
        }
    }
}