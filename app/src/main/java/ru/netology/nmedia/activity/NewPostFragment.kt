package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.Gravity
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
import ru.netology.nmedia.activity.util.StringArg
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.dto.models.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class NewPostFragment : Fragment() {

    companion object {
        const val EDIT_POST = "edit_post"
        const val POST_CONTENT = "edit_post_content"
        var Bundle.editPostArg: Post? by PostArg
        var Bundle.textArg: String? by StringArg
    }

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    //private val viewModel: PostViewModel by activityViewModels()

    private var post: Post? = null
    private var textForView: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentNewPostBinding.inflate(inflater, container, false)

        post = arguments?.editPostArg
        //Если передан postArg, берем контент поста, если передан textArg, берем переданный текст
        textForView = arguments?.textArg ?: post?.content

        textForView.let(binding.edit::setText)

        binding.edit.requestFocus()

        binding.ok.setOnClickListener {
            val content = binding.edit.text.toString()
            //Изменения сохраняем только для нового или редактируемого поста.
            //Если textArg не заполнен, значит редактируем или добавляем пост. Иначе - отображаем текст, полученный через ACTION_SEND
            if (arguments?.textArg.isNullOrBlank()) {
                if (!content.isNullOrBlank()) {
                    if (post != null) viewModel.edit(post!!)
                    viewModel.changeContent(content)
                    viewModel.save()
                } else {
                    val toast =
                        Toast.makeText(
                            requireContext(),
                            R.string.error_empty_post_content,
                            Toast.LENGTH_SHORT
                        )
                    toast.apply {
                        setGravity(Gravity.CENTER, 0, 0)
                        show()
                    }
                }
            }
            AndroidUtils.hideKeyboard(requireView())
            findNavController().navigateUp()
        }
        return binding.root
    }
}