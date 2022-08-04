package com.androidpositive.ghubstore.presentation.repositorylist.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.androidpositive.ghubstore.R
import com.androidpositive.ghubstore.databinding.FragmentRepositoryListBinding
import com.androidpositive.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepositoryListFragment : Fragment(R.layout.fragment_repository_list) {
    private val binding by viewBinding(FragmentRepositoryListBinding::bind)
    private val viewModel by activityViewModels<RepositoryListViewModel>()
    private val repositoryListAdapter by lazy {
        RepositoryListAdapter(
            requireView().findViewById(R.id.repository_detail_nav_container)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.repository.observe(viewLifecycleOwner) {
            it.success { repositories ->
                repositoryListAdapter.submitList(repositories)
            }
            it.failure {
            }
        }
    }
}
