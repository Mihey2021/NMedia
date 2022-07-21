package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.util.AndroidUtils
import ru.netology.nmedia.activity.util.PostArg
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.dto.models.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class NewPostFragment : Fragment() {

    companion object {
        const val EDIT_POST = "edit_post"
        const val POST_CONTENT = "edit_post_content"
        var Bundle.postArg: Post? by PostArg
    }

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    private var post: Post? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentNewPostBinding.inflate(inflater, container, false)

//        intent?.let { receivedIntent ->
//            post = receivedIntent.getSerializableExtra(EDIT_POST) as? Post
//            val text = post?.content
//            if (!text.isNullOrBlank()) {
//                binding.edit.setText(text)
//            }
//        }

//        arguments?.textArg
//            ?.let(binding.edit::setText)
        arguments?.postArg
            ?.content?.let(binding.edit::setText)

        post = arguments?.postArg

        binding.edit.requestFocus()

        binding.ok.setOnClickListener {
            val content = binding.edit.text.toString()
            if (post != null) viewModel.edit(post!!)
            viewModel.changeContent(content)
            viewModel.save()
            AndroidUtils.hideKeyboard(requireView())
            findNavController().navigateUp()
        }
        return binding.root
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val binding = ActivityNewPostBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        intent?.let { receivedIntent ->
//            post = receivedIntent.getSerializableExtra(EDIT_POST) as? Post
//            val text = post?.content
//            if (!text.isNullOrBlank()) {
//                binding.edit.setText(text)
//            }
//        }
//
//        binding.edit.requestFocus()
//
//        binding.ok.setOnClickListener {
//            val intent = Intent()
//            val content = binding.edit.text.toString()
//
//            if (content.isBlank()) {
//                Toast.makeText(
//                    this,
//                    R.string.error_empty_post_content,
//                    Toast.LENGTH_SHORT
//                ).show()
//                setResult(Activity.RESULT_CANCELED, intent)
//            } else {
//                intent.putExtra(POST_CONTENT, content)
//                intent.putExtra(EDIT_POST, post)
//                setResult(Activity.RESULT_OK, intent)
//            }
//
//            finish()
//        }
//    }
}