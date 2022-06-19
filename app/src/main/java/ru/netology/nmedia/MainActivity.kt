package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.functions.prepareCountToDisplay

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            published = "21 мая в 18:36",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            likes = 999_999,
            shared = 9_998,
            views = 5_997
        )

        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likesText.text = prepareCountToDisplay(post.likes)
            shareText.text = prepareCountToDisplay(post.shared)
            viewsText.text = prepareCountToDisplay(post.views)
            avatar.setImageResource(R.drawable.ic_launcher_foreground)

            if (post.likedByMe) likes.setImageResource(R.drawable.ic_liked_24)

            likes?.setOnClickListener {
                post.likedByMe = !post.likedByMe
                likes.setImageResource(if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24)
                if (post.likedByMe) post.likes++ else post.likes--

                likesText?.text = prepareCountToDisplay(post.likes)
            }

            share?.setOnClickListener {
                post.shared++
                shareText?.text = prepareCountToDisplay(post.shared)
            }
        }
    }
}