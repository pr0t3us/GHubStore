package com.androidpositive.ghubstore.presentation.repositorylist.list.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.androidpositive.ghubstore.databinding.ItemRepositoryListBinding
import com.androidpositive.ghubstore.presentation.repositorylist.RepositoryUiModel

class RepositoryListViewHolder(
    private val binding: ItemRepositoryListBinding
) : ViewHolder(binding.root) {

    fun bind(item: RepositoryUiModel) {
        with(binding) {
            name.text = item.name
            description.text = item.description
        }
    }
}
