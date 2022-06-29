package ru.netology.nmedia.dto.models

import ru.netology.nmedia.R

data class Post(
    val id: Long,
    val author: String,
    val avatar: Int = R.drawable.ic_launcher_foreground,
    val content: String,
    val published: String,
    val likes: Int = 0,
    val likedByMe: Boolean = false,
    val shared: Int = 0,
    val views: Int = 0
)