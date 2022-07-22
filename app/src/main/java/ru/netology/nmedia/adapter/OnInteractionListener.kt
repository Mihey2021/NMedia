package ru.netology.nmedia.adapter

import ru.netology.nmedia.dto.models.Post

interface OnInteractionListener {
    fun onLike(post: Post) {}
    fun onEdit(post: Post) {}
    fun onShare(post: Post) {}
    fun onRemove(post: Post) {}
    fun onPlay(url: String?) {}
    fun onPostView(post: Post) {}
}