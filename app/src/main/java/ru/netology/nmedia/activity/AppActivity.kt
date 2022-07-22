package ru.netology.nmedia.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.EDIT_POST
import ru.netology.nmedia.activity.NewPostFragment.Companion.POST_CONTENT
import ru.netology.nmedia.activity.NewPostFragment.Companion.editPostArg
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.dto.models.Post

class AppActivity : AppCompatActivity(R.layout.activity_app) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent?.let { receivedIntent ->
            if (receivedIntent.action != Intent.ACTION_SEND) return@let

            val text = receivedIntent.getStringExtra(Intent.EXTRA_TEXT)
            val post = receivedIntent.getSerializableExtra(EDIT_POST) as? Post
//            if (text.isNullOrBlank()) {
//                if (post?.content.isNullOrBlank()) {
//                    val toast =
//                        Toast.makeText(this, R.string.error_empty_post_content, Toast.LENGTH_SHORT)
//                    toast.apply {
//                        setGravity(Gravity.CENTER, 0, 0)
//                        show()
//                    }
//                }
//            }
            intent.removeExtra(Intent.EXTRA_TEXT)
            intent.removeExtra(EDIT_POST)
            findNavController(R.id.nav_host_fragment).navigate(
                R.id.action_feedFragment_to_newPostFragment,
                Bundle().apply {
                    editPostArg = post
                    textArg = text
                }
            )
        }
    }
}