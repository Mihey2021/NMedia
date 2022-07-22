package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.domain.repository.PostRepository
import ru.netology.nmedia.dto.models.Post
import ru.netology.nmedia.dto.repository.PostRepositoryFileImpl

private val empty = Post(
    id = 0,
    content = "",
    author = "",
    published = ""
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    //Репозиторий с данными по умолчанию, без хранения
    //private val repository: PostRepository = PostRepositoryImpl()

    //Репозиторий с хранением в SharedPreferences
    //private val repository: PostRepository = PostRepositorySharedPrefsImpl(application)

    //Репозиторий с хранением в файле
    private val repository: PostRepository = PostRepositoryFileImpl(application)

    val data = repository.getAll()
    private val edited = MutableLiveData(empty)

    fun save() {
        edited.value?.let { repository.save(it) }
        clearEditingData()
    }

    fun edit(post: Post) {
        edited.value = post
    }

    private fun clearEditingData() {
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