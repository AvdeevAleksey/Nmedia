package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostViewHolder
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.FragmentCardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.AndroidUtils
import ru.netology.nmedia.viewmodel.PostViewModel

class CardPostFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentCardPostBinding.inflate(inflater, container, false)

        val post : Post? = arguments?.getParcelable<Post>(AndroidUtils.POST_KEY)
        arguments?.remove(AndroidUtils.POST_KEY)

        val postsAdapter = PostsAdapter (object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }
            override fun onShare(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT,post.content)
                    type = "text/plane"
                }
                val shareIntent =
                    Intent.createChooser(intent,getString(R.string.chooser_share_post))
                startActivity(shareIntent)
                viewModel.shareById(post.id)
            }
            override fun onViewing(post: Post) {
                viewModel.viewingById(post.id)
            }
            override fun onPostEdit(post: Post) {
                findNavController().navigate(
                    R.id.action_feedFragment_to_postFragment,
                    bundleOf("post" to post)
                )
                viewModel.editPost(post)
            }
            override fun onPostRemove(post: Post) {
                viewModel.removeById(post.id)
            }
            override fun onPlayVideo(post: Post) {
                val intent = Intent().apply {
                    Intent.ACTION_VIEW
                    Uri.parse(post.videoInPost).takeIf {
                        it != null
                    }
                }
                val playVideoIntent = Intent.createChooser(intent,getString(R.string.play_video_app_chooser))
                startActivity(playVideoIntent)
            }

            override fun onPostOpen(post: Post) {
                TODO("Not yet implemented")
            }
        })

//        binding.cardPost.???? = postsAdapter
//        TODO("I don't know where to submit a postAdapter")

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            postsAdapter.submitList(posts)
        }

        viewModel.edited.observe(viewLifecycleOwner) { post ->
            if (post.id == 0) {
                return@observe
            }
        }

        return binding.root
    }
}