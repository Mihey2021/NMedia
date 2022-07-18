package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.models.Post
import ru.netology.nmedia.functions.prepareCountToDisplay

class PostsAdapter(private val onInteractionListener: OnInteractionListener) :
    ListAdapter<Post, PostsAdapter.PostHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }

    class PostHolder(
        private val binding: CardPostBinding,
        private val onInteractionListener: OnInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            binding.apply {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                likes.text = prepareCountToDisplay(post.likes)
                share.text = prepareCountToDisplay(post.shared)
                views.text = prepareCountToDisplay(post.views)
                avatar.setImageResource(post.avatar)

                videoPreviewGroup.visibility = if (post.video.isNullOrBlank()) View.GONE else View.VISIBLE
                videoPreviewGroup.setOnClickListener {
                    onInteractionListener.onPlay(post.video)
                }

                likes.isChecked = post.likedByMe
                likes.setOnClickListener {
                    onInteractionListener.onLike(post)
                }

                menu.setOnClickListener {
                    PopupMenu(it.context, it).apply {
                        inflate(R.menu.options_post)
                        setOnMenuItemClickListener { menuItem ->
                            when (menuItem.itemId) {
                                R.id.remove -> {
                                    onInteractionListener.onRemove(post)
                                    true
                                }
                                R.id.edit -> {
                                    onInteractionListener.onEdit(post)
                                    true
                                }
                                else -> false
                            }

                        }
                    }.show()
                }

                share.setOnClickListener {
                    onInteractionListener.onShare(post)
                }
            }
        }
    }

    class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }

        //Еще один способ не применять анимацию (убрать "мерцание")
        override fun getChangePayload(oldItem: Post, newItem: Post): Any = Unit
    }
}

