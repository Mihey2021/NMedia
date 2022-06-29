package ru.netology.nmedia.domain.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.models.Post

interface PostRepository {
    fun get(): LiveData<Post>
    fun like()
    fun share()
    
}