package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.util.PostArg
import ru.netology.nmedia.databinding.FragmentViewPostBinding
import ru.netology.nmedia.dto.models.Post

class ViewPostFragment : Fragment(R.layout.card_post) {

    companion object {
        var Bundle.viewPostArg: Post? by PostArg
    }

    private var post: Post? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentViewPostBinding.inflate(inflater, container, false)

        post = arguments?.viewPostArg


        return binding.root
        //return inflater.inflate(R.layout.card_post, container, false)
    }

}
