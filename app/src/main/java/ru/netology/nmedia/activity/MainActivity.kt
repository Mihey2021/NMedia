package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.launch
import androidx.activity.viewModels
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.models.Post
import ru.netology.nmedia.viewmodel.PostViewModel
import java.net.URI

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val editPostLauncher = registerForActivityResult(EditPostResultContract()) { result ->
            result ?: return@registerForActivityResult
            result.first ?: return@registerForActivityResult
            result.second ?: return@registerForActivityResult
            viewModel.edit(result.first!!)
            viewModel.changeContent(result.second!!)
            viewModel.save()
        }

        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }

                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)

                viewModel.shareById(post.id)
            }

            override fun onRemove(post: Post) = viewModel.removeById(post.id)

            override fun onEdit(post: Post) = editPostLauncher.launch(post)

            override fun onPlay(url: String?) {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(url)
                ).setPackage("com.google.android.youtube")
                if (intent.resolveActivity(packageManager) == null) {
                    Snackbar.make(
                        binding.root, R.string.warning_youtube_not_installed,
                        BaseTransientBottomBar.LENGTH_INDEFINITE
                    )
                        .setAction(R.string.description_open_in_browser) {
                            val intentForBrowser = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            startActivity(intentForBrowser)
                        }
                        .show()
                } else {
                    startActivity(intent)
                }
            }
        }
        )

        binding.list.adapter = adapter

//        //Один из способов отключить анимацию
//        (binding.list.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        val newPostLauncher = registerForActivityResult(NewPostResultContract()) { result ->
            result ?: return@registerForActivityResult
            viewModel.changeContent(result)
            viewModel.save()
        }

        binding.fab.setOnClickListener {
            newPostLauncher.launch()
        }
    }
}