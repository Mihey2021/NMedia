package ru.netology.nmedia.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.SimpleItemAnimator
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.models.Post
import ru.netology.nmedia.activity.util.AndroidUtils
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val cancelEditGroup = binding.cancelEditingGroup

        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                viewModel.shareById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }
        }
        )

        binding.list.adapter = adapter

//        //Один из способов отключить анимацию
//        (binding.list.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        binding.cancelEditPostBtn.setOnClickListener {
            viewModel.clearEditingData()
            AndroidUtils.clearTextAndHideKeyboard(binding.content)
            AndroidUtils.hideCancelEditGroup(cancelEditGroup)
        }

        binding.save.setOnClickListener {
            with(binding.content) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        R.string.error_empty_post_content,
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                viewModel.changeContent(text.toString())
                viewModel.save()

                AndroidUtils.clearTextAndHideKeyboard(binding.content)
            }
        }

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        viewModel.edited.observe(this) { post ->
            if (post.id == 0L) {
                AndroidUtils.hideCancelEditGroup(cancelEditGroup)
                return@observe
            } else {
                AndroidUtils.showCancelEditGroup(cancelEditGroup)
            }

            with(binding.content) {
                requestFocus()
                setText(post.content)
            }
        }
    }
}