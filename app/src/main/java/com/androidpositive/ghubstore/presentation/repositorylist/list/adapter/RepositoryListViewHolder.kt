package com.androidpositive.ghubstore.presentation.repositorylist.list.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.androidpositive.ghubstore.databinding.RepositoryListContentBinding
import com.androidpositive.ghubstore.presentation.repositorylist.RepositoryUiModel

class RepositoryListViewHolder(
    private val binding: RepositoryListContentBinding
) : ViewHolder(binding.root) {

    fun bind(item: RepositoryUiModel) {
        with(binding) {
            idText.text = item.name
            content.text = item.description
        }
    }
}
