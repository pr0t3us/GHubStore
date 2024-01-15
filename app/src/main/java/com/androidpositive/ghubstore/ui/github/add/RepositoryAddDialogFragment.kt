package com.androidpositive.ghubstore.ui.github.add

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.androidpositive.ghubstore.R
import com.androidpositive.ghubstore.databinding.DialogRepositoryAddBinding
import com.androidpositive.ghubstore.ui.github.list.adapter.RepositoryListAdapter
import com.androidpositive.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepositoryAddDialogFragment : DialogFragment(R.layout.dialog_repository_add) {
    private val binding by viewBinding(DialogRepositoryAddBinding::bind)
    private val viewModel: RepositoryAddViewModel by viewModels<RepositoryAddViewModelImpl>()
    private val listAdapter = RepositoryListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.repositoryAddFullName.doOnTextChanged { text, _, _, _ ->
            viewModel.onSearchRepository(text.toString())
        }
        binding.repositoryAddCta.setOnClickListener {
            viewModel.onCtaClicked(binding.repositoryAddFullName.text.toString())
        }
        binding.repositoryAddList.adapter = listAdapter
        binding.repositoryAddList.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )
        viewModel.repositories.observe(viewLifecycleOwner) {
            it.success { repositories ->
                listAdapter.submitList(repositories)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
    }
}
