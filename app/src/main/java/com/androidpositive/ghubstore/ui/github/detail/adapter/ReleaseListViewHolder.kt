package com.androidpositive.ghubstore.ui.github.detail.adapter

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.androidpositive.ghubstore.databinding.ItemReleaseListBinding
import org.kohsuke.github.GHAsset
import org.kohsuke.github.GHRelease

class ReleaseListViewHolder(
    private val binding: ItemReleaseListBinding
) : ViewHolder(binding.root) {

    fun bind(item: GHRelease, onAssetClick: (GHAsset) -> Unit) {
        with(binding) {
            name.text = item.name
            item.listAssets().onEach { asset ->
                root.addView(
                    TextView(binding.root.context).apply {
                        text = asset.name
                        setOnClickListener { onAssetClick(asset) }
                    })
            }
        }
    }
}
