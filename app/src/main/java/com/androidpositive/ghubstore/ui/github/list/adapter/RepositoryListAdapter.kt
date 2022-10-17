package com.androidpositive.ghubstore.ui.github.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.androidpositive.ghubstore.databinding.ItemRepositoryListBinding
import com.androidpositive.ghubstore.ui.github.RepositoryUiModel

class RepositoryListAdapter :
    ListAdapter<RepositoryUiModel, RepositoryListViewHolder>(RepositoryUiModelDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryListViewHolder {
        val binding = ItemRepositoryListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return RepositoryListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepositoryListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
