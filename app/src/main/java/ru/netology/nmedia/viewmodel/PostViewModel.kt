package ru.netology.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.domain.repository.PostRepository
import ru.netology.nmedia.dto.repository.PostRepositoryImpl

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryImpl()
    val data = repository.get()
    fun like() = repository.like()
    fun share() = repository.share()
}