package com.androidpositive.ghubstore.presentation.repositorylist.detail.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.androidpositive.ghubstore.databinding.ItemRepositoryListBinding
import org.kohsuke.github.GHRelease

class ReleaseListViewHolder(
    private val binding: ItemRepositoryListBinding
) : ViewHolder(binding.root) {

    fun bind(item: GHRelease) {
        with(binding) {
            name.text = item.name
            description.text = item.body
        }
    }
}
