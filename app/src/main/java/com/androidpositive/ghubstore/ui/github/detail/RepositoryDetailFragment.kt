package com.androidpositive.ghubstore.ui.github.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.androidpositive.ghubstore.R
import com.androidpositive.ghubstore.databinding.FragmentRepositoryDetailBinding
import com.androidpositive.ghubstore.ui.github.detail.adapter.ReleaseListAdapter
import com.androidpositive.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepositoryDetailFragment : Fragment(R.layout.fragment_repository_detail) {
    private val binding by viewBinding(FragmentRepositoryDetailBinding::bind)
    private val listAdapter = ReleaseListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.repositoryList.adapter = listAdapter

        val args = arguments?.let {
            RepositoryDetailFragmentArgs.fromBundle(it)
        }
        binding.toolbarLayout?.title = args?.repositoryItem?.name
        binding.repositoryDetail.text = args?.repositoryItem?.description
        listAdapter.submitList(args?.repositoryItem?.releases)
    }
}
