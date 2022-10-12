package com.androidpositive.ghubstore.presentation.repositorylist.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.androidpositive.ghubstore.databinding.ItemRepositoryListBinding
import org.kohsuke.github.GHRelease

class ReleaseListAdapter : ListAdapter<GHRelease, ReleaseListViewHolder>(ReleaseModelDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReleaseListViewHolder {
        val binding = ItemRepositoryListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ReleaseListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReleaseListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
