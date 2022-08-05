package com.androidpositive.ghubstore.presentation.repositorylist.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.androidpositive.extensions.ItemClickSupport.Companion.setOnItemClickListener
import com.androidpositive.extensions.ItemClickSupport.OnItemClickListener
import com.androidpositive.ghubstore.R
import com.androidpositive.ghubstore.databinding.FragmentRepositoryListBinding
import com.androidpositive.ghubstore.presentation.repositorylist.detail.RepositoryDetailFragment
import com.androidpositive.viewbinding.viewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepositoryListFragment : Fragment(R.layout.fragment_repository_list) {
    private val binding by viewBinding(FragmentRepositoryListBinding::bind)
    private val viewModel by activityViewModels<RepositoryListViewModel>()
    private val repositoryListAdapter = RepositoryListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.repositoryList.adapter = repositoryListAdapter

        binding.repositoryList.setOnItemClickListener(
            object : OnItemClickListener {
                override fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View) {
                    navigateToRepositoryDetails(v, position, binding.repositoryDetailNavContainer)
                }
            })
        viewModel.repository.observe(viewLifecycleOwner) {
            it.success { repositories ->
                repositoryListAdapter.submitList(repositories)
            }
            it.failure {
                Snackbar.make(
                    binding.repositoryListContainer,
                    getString(R.string.general_error),
                    Snackbar.LENGTH_SHORT
                ).show()

            }
        }
    }

    private fun navigateToRepositoryDetails(
        itemView: View,
        position: Int,
        itemDetailFragmentContainer: View?
    ) {
        val repository = repositoryListAdapter.list[position]
        val bundle = Bundle()
        bundle.putLong(
            RepositoryDetailFragment.ARG_ITEM_ID,
            repository.id
        )
        if (itemDetailFragmentContainer != null) {
            itemDetailFragmentContainer.findNavController()
                .navigate(R.id.fragment_repository_detail, bundle)
        } else {
            itemView.findNavController().navigate(R.id.show_repository_detail, bundle)
        }
    }
}
