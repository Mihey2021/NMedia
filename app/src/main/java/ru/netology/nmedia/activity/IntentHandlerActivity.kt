package ru.netology.nmedia.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityIntentHandlerBinding

class IntentHandlerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityIntentHandlerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent?.let { receivedIntent ->
            if (receivedIntent.action != Intent.ACTION_SEND) return@let

            binding.readingOk.setOnClickListener {
                finish()
            }

            val text = receivedIntent.getStringExtra(Intent.EXTRA_TEXT)
            if (text.isNullOrBlank()) {
                Snackbar.make(binding.root, R.string.error_empty_post_content, LENGTH_INDEFINITE)
                    .setAction(R.string.str_understandably_ok) {
                        finish()
                    }
                    .show()
                return@let
            }

            binding.receivedTextView.text = text
        }
    }
}