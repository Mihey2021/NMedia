package ru.netology.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.domain.repository.PostRepository
import ru.netology.nmedia.dto.repository.PostRepositoryImpl

class PostViewModel() : ViewModel() {
    private val repository: PostRepository = PostRepositoryImpl()
    val data = repository.get()
    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
}