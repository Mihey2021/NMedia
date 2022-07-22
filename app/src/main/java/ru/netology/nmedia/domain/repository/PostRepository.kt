package ru.netology.nmedia.domain.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.models.Post

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun save(post: Post)
    fun likeById(id: Long)
    fun shareById(id: Long)
    fun removeById(id: Long)
}