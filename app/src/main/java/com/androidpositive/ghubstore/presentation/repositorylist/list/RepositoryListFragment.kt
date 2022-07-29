package com.androidpositive.ghubstore.presentation.repositorylist.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.androidpositive.ghubstore.R
import com.androidpositive.ghubstore.databinding.FragmentRepositoryListBinding
import com.androidpositive.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import org.kohsuke.github.GHRepository

@AndroidEntryPoint
class RepositoryListFragment : Fragment(R.layout.fragment_repository_list) {
    private val binding by viewBinding(FragmentRepositoryListBinding::bind)
    private val viewModel by activityViewModels<RepositoryListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.repository.observe(viewLifecycleOwner) {
            it.success { repositories ->
                setupRecyclerView(
                    binding.repositoryList,
                    repositories,
                    view.findViewById(R.id.repository_detail_nav_container)
                )
            }
            it.failure {
            }
        }
    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        repositories: List<GHRepository>,
        itemDetailFragmentContainer: View?
    ) {
        recyclerView.adapter = RepositoryListAdapter(
            repositories,
            itemDetailFragmentContainer
        )
    }
}
