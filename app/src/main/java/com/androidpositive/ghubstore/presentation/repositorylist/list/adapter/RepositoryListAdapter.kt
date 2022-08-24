package com.androidpositive.ghubstore.presentation.repositorylist.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.androidpositive.ghubstore.databinding.RepositoryListContentBinding
import com.androidpositive.ghubstore.presentation.repositorylist.RepositoryUiModel

class RepositoryListAdapter :
    ListAdapter<RepositoryUiModel, RepositoryListViewHolder>(RepositoryUiModelDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryListViewHolder {
        val binding = RepositoryListContentBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return RepositoryListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepositoryListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}