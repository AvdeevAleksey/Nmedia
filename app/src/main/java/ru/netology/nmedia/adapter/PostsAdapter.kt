package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post

typealias OnLikeListener = (post: Post) -> Unit
typealias OnShareListener = (post: Post) -> Unit
typealias OnViewingListener = (post: Post) -> Unit

class PostsAdapter(
    private val onLikeListener: OnLikeListener,
    private val onShareListener: OnShareListener,
    private val onViewingListener: OnViewingListener
) : ListAdapter<Post, PostViewHolder>(PostDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostViewHolder(binding, onLikeListener, onShareListener, onViewingListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class PostDiffCallBack : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onLikeListener: OnLikeListener,
    private val onShareListener: OnShareListener,
    private val onViewingListener: OnViewingListener
) : RecyclerView.ViewHolder(binding.root)  {
    fun bind(post: Post) {
        binding.apply {
            authorTextView.text = post.author
            publishedTextView.text = post.published
            contentTextView.text = post.content
            likeTextView.text = countMyClick(post.likesCount)
            repostsTextView.text = countMyClick(post.repostsCount)
            viewsTextView.text = countMyClick(post.viewsCount)
            likeImageView.setImageResource(
                if (post.likedByMe) R.drawable.ic_liked_favorite_24 else R.drawable.ic_baseline_favorite_border_24
            )
            likeImageView.setOnClickListener {
                onLikeListener(post)
            }
            repostsImageView.setOnClickListener {
                onShareListener(post)
            }
            viewsImageView.setOnClickListener {
                onViewingListener(post)
            }
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
        in click/(t*t)%10*(t*t)+t*100 until click/(t*t)%10*(t*t)+(t*t) -> "${click/(t*t)%10},${click/(t*100)-click/(t*t)%10*10}${dischargesReduction(click)}"
        else -> "${click/(t*t)}${dischargesReduction(click)}"
    }
}