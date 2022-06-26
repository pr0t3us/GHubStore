package com.androidpositive.ghubstore.presentation.repositorylist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.androidpositive.ghubstore.R
import com.androidpositive.ghubstore.databinding.FragmentRepositoryListBinding
import com.androidpositive.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepositoryListFragment : Fragment(R.layout.fragment_repository_list) {
    private val binding by viewBinding(FragmentRepositoryListBinding::bind)
    private val viewModel by viewModels<RepositoryListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_RepositoryListFragment_to_SecondFragment)
        }

        viewModel
    }
}
