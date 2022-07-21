package ru.netology.nmedia.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.navigation.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.EDIT_POST
import ru.netology.nmedia.activity.NewPostFragment.Companion.POST_CONTENT
import ru.netology.nmedia.activity.NewPostFragment.Companion.postArg
import ru.netology.nmedia.dto.models.Post

class AppActivity : AppCompatActivity(R.layout.activity_app) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val binding = ActivityIntentHandlerBinding.inflate(layoutInflater)
//        setContentView(binding.root)

        intent?.let { receivedIntent ->
            if (receivedIntent.action != Intent.ACTION_SEND) return@let

//            binding.readingOk.setOnClickListener {
//                finish()
//            }

            //val text = receivedIntent.getStringExtra(Intent.EXTRA_TEXT)
            val post = receivedIntent.getSerializableExtra(EDIT_POST) as? Post
            //if (text.isNullOrBlank()) {
            if (post?.content.isNullOrBlank()) {
//                Snackbar.make(binding.root, R.string.error_empty_post_content, LENGTH_INDEFINITE)
//                    .setAction(R.string.str_understandably_ok) {
//                        finish()
//                    }
//                    .show()
                return@let
            }
            //binding.receivedTextView.text = text
            intent.removeExtra(EDIT_POST)
            findNavController(R.id.nav_host_fragment).navigate(
                R.id.action_feedFragment_to_newPostFragment,
                Bundle().apply { postArg = post }
            )
        }
    }
}