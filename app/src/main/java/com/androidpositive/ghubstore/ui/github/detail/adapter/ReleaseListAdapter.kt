package com.androidpositive.ghubstore.ui.github.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.androidpositive.ghubstore.databinding.ItemReleaseListBinding
import org.kohsuke.github.GHAsset
import org.kohsuke.github.GHRelease

class ReleaseListAdapter(
    private val onAssetClick: (GHAsset) -> Unit = {}
) : ListAdapter<GHRelease, ReleaseListViewHolder>(ReleaseModelDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReleaseListViewHolder {
        val binding = ItemReleaseListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ReleaseListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReleaseListViewHolder, position: Int) {
        holder.bind(getItem(position), onAssetClick)
    }
}
