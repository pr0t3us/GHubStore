package com.androidpositive.ghubstore.presentation.repositorylist.list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.androidpositive.ghubstore.presentation.repositorylist.RepositoryUiModel

object RepositoryUiModelDiff : DiffUtil.ItemCallback<RepositoryUiModel>() {
    override fun areItemsTheSame(oldItem: RepositoryUiModel, newItem: RepositoryUiModel) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: RepositoryUiModel, newItem: RepositoryUiModel) =
        oldItem == newItem
}
