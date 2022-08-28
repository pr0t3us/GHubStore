package com.androidpositive.ghubstore.presentation.repositorylist.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.androidpositive.ghubstore.R
import com.androidpositive.ghubstore.databinding.FragmentRepositoryDetailBinding
import com.androidpositive.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepositoryDetailFragment : Fragment(R.layout.fragment_repository_detail) {
    private val binding by viewBinding(FragmentRepositoryDetailBinding::bind)
    private val args: RepositoryDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarLayout?.title = args.repositoryItem.name
        binding.repositoryDetail.text = args.repositoryItem.description
    }
}
