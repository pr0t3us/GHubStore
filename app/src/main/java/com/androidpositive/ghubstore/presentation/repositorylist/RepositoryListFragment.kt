package com.androidpositive.ghubstore.presentation.repositorylist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.androidpositive.extensions.hide
import com.androidpositive.extensions.show
import com.androidpositive.ghubstore.R
import com.androidpositive.ghubstore.databinding.FragmentRepositoryListBinding
import com.androidpositive.ghubstore.presentation.repositorylist.list.RepositoryListAdapter
import com.androidpositive.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import org.kohsuke.github.GHRepository

@AndroidEntryPoint
class RepositoryListFragment : Fragment(R.layout.fragment_repository_list) {
    private val binding by viewBinding(FragmentRepositoryListBinding::bind)
    private val viewModel by activityViewModels<RepositoryListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.repository.observe(viewLifecycleOwner) {
            it.loading {
                binding.progressLayout.progressContainer.show()
            }
            it.success { repositories ->
                binding.progressLayout.progressContainer.hide()
                setupRecyclerView(
                    binding.repositoryList,
                    repositories,
                    view.findViewById(R.id.repository_detail_nav_container)
                )
            }
            it.failure {
                binding.progressLayout.progressContainer.hide()
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
