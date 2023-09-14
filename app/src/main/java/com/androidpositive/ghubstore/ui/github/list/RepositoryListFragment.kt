package com.androidpositive.ghubstore.ui.github.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.androidpositive.extensions.ItemClickSupport.Companion.setOnItemClickListener
import com.androidpositive.extensions.ItemClickSupport.OnItemClickListener
import com.androidpositive.ghubstore.R
import com.androidpositive.ghubstore.databinding.FragmentRepositoryListBinding
import com.androidpositive.ghubstore.ui.github.list.adapter.RepositoryListAdapter
import com.androidpositive.viewbinding.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepositoryListFragment : Fragment(R.layout.fragment_repository_list) {
    private val binding by viewBinding(FragmentRepositoryListBinding::bind)
    private val viewModel: RepositoryListViewModel
            by activityViewModels<RepositoryListViewModelImpl>()
    private val listAdapter = RepositoryListAdapter()
    private val repositoryListItemClickListener = object : OnItemClickListener {
        override fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View) {
            val navController = (binding.repositoryDetailNavContainer ?: v).findNavController()
            viewModel.onItemClicked(position, navController)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.repositoryList.adapter = listAdapter
        binding.repositoryList.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )

        binding.repositoryList.setOnItemClickListener(repositoryListItemClickListener)
        viewModel.repositories.observe(viewLifecycleOwner) {
            it.success { repositories ->
                listAdapter.submitList(repositories)
            }
            it.failure {
                Logger.e("viewModel.repositories failure:$it")
                Snackbar.make(
                    binding.repositoryListContainer,
                    getString(R.string.general_error),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
        viewModel.detailsNavigationUiModel.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.asSuccess()?.data?.let { navigationModel ->
                navigateToRepositoryDetails(navigationModel)
            }
        }
    }

    private fun navigateToRepositoryDetails(navigationModel: RepositoryDetailNavigationModel) {
        val action = RepositoryListFragmentDirections.showRepositoryDetail(
            navigationModel.repositoryDetailUiModel
        )
        navigationModel.navController.navigate(action)
    }
}
