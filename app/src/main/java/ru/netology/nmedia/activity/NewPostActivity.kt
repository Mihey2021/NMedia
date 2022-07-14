package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.util.AndroidUtils
import ru.netology.nmedia.databinding.ActivityNewPostBinding
import ru.netology.nmedia.dto.models.Post

class NewPostActivity : AppCompatActivity() {

    companion object {
        const val EDIT_POST = "edit_post"
        const val POST_CONTENT = "edit_post_content"
    }

    private var post: Post? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent?.let { receivedIntent ->
            post = receivedIntent.getSerializableExtra(EDIT_POST) as? Post
            val text = post?.content
            if (!text.isNullOrBlank()) {
                binding.edit.setText(text)
            }
        }

        binding.edit.requestFocus()

        binding.ok.setOnClickListener {
            val intent = Intent()
            val content = binding.edit.text.toString()

            if (content.isBlank()) {
                Toast.makeText(
                    this,
                    R.string.error_empty_post_content,
                    Toast.LENGTH_SHORT
                ).show()
                setResult(Activity.RESULT_CANCELED, intent)
            } else {
                intent.putExtra(POST_CONTENT, content)
                intent.putExtra(EDIT_POST, post)
                setResult(Activity.RESULT_OK, intent)
            }

            finish()
        }
    }
}