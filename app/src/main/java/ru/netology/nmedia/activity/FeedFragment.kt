package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.EDIT_POST
import ru.netology.nmedia.activity.NewPostFragment.Companion.postArg
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.models.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class FeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentFeedBinding.inflate(inflater, container, false)

        val viewModel: PostViewModel by viewModels()

//        val editPostLauncher = registerForActivityResult(EditPostResultContract()) { result ->
//            result ?: return@registerForActivityResult
//            result.first ?: return@registerForActivityResult
//            result.second ?: return@registerForActivityResult
//            viewModel.edit(result.first!!)
//            viewModel.changeContent(result.second!!)
//            viewModel.save()
//        }

        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    //putExtra(EDIT_POST, post)
                    type = "text/plain"
                }

                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)

                viewModel.shareById(post.id)
            }

            override fun onRemove(post: Post) = viewModel.removeById(post.id)

            //override fun onEdit(post: Post) = editPostLauncher.launch(post)
            override fun onEdit(post: Post) {
//                val intent = Intent(requireContext(), NewPostFragment::class.java)
//                    .putExtra(NewPostFragment.EDIT_POST, post)
//                startActivity(intent)
                findNavController().navigate(R.id.action_feedFragment_to_newPostFragment,
                    Bundle().apply { postArg = post })
            }

            override fun onPlay(url: String?) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
                //Пример проверки установлено ли конкретное приложение (в данном случае - youtube)
//                val intent = Intent(
//                    Intent.ACTION_VIEW,
//                    Uri.parse(url)
//                ).setPackage("com.google.android.youtube")
//                if (intent.resolveActivity(packageManager) == null) {
//                    Snackbar.make(
//                        binding.root, R.string.warning_youtube_not_installed,
//                        BaseTransientBottomBar.LENGTH_INDEFINITE
//                    )
//                        .setAction(R.string.description_open_in_browser) {
//                            val intentForBrowser = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                            startActivity(intentForBrowser)
//                        }
//                        .show()
//                } else {
//                    startActivity(intent)
//                }
            }
        }
        )

        binding.list.adapter = adapter

//        //Один из способов отключить анимацию
//        (binding.list.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }

//        val newPostLauncher = registerForActivityResult(NewPostResultContract()) { result ->
//            result ?: return@registerForActivityResult
//            viewModel.changeContent(result)
//            viewModel.save()
//        }

        binding.fab.setOnClickListener {
            //newPostLauncher.launch()
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }

        return binding.root
    }
}