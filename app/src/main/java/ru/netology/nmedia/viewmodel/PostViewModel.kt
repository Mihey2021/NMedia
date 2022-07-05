package ru.netology.nmedia.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.domain.repository.PostRepository
import ru.netology.nmedia.dto.models.Post
import ru.netology.nmedia.dto.repository.PostRepositoryImpl

private val empty = Post(
    id = 0,
    content = "",
    author = "",
    published = ""
)

class PostViewModel() : ViewModel() {
    private val repository: PostRepository = PostRepositoryImpl()
    val data = repository.get()
    val edited = MutableLiveData(empty)

    fun save() {
        edited.value?.let { repository.save(it) }
        clearEditingData()
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun clearEditingData() {
        edited.value = empty
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) return
        edited.value = edited.value?.copy(content = text)
    }

    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun removeById(id: Long) = repository.removeById(id)
}