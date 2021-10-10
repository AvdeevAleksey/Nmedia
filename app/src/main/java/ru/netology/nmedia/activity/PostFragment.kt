package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.FragmentPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.AndroidUtils
import ru.netology.nmedia.utils.AndroidUtils.POST_KEY
import ru.netology.nmedia.viewmodel.PostViewModel

class PostFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPostBinding.inflate(inflater, container, false)
        binding.editPostContent.requestFocus()

        val post : Post = arguments?.getParcelable<Post>(POST_KEY) as Post
        arguments?.remove(POST_KEY)

        with(binding.editPostContent) {
            if (post != null && !post.content.isNullOrBlank()) {
                text.append(post.content)
            } else {
                text.append("")
            }
        }

        binding.fabOk.setOnClickListener {
            if (!binding.editPostContent.text.isNullOrBlank()) {
                val content = binding.editPostContent.text.toString()
                viewModel.changeContent(content)
                viewModel.savePost()
            }
            AndroidUtils.hideKeyboard(binding.root)
            findNavController().navigateUp()
        }
        return binding.root
    }
}