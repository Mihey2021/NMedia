package ru.netology.nmedia.dto.models

import ru.netology.nmedia.R
import java.io.Serializable

data class Post(
    val id: Long = 0L,
    val author: String,
    val avatar: Int = R.drawable.ic_launcher_foreground,
    val content: String,
    val published: String,
    val likes: Int = 0,
    val likedByMe: Boolean = false,
    val shared: Int = 0,
    val views: Int = 0,
    val video: String? = null
) : Serializable