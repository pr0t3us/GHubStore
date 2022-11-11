package com.androidpositive.ghubstore.ui.github.detail.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import org.kohsuke.github.GHRelease

object ReleaseModelDiff : DiffUtil.ItemCallback<GHRelease>() {
    override fun areItemsTheSame(oldItem: GHRelease, newItem: GHRelease) =
        oldItem.id == newItem.id

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: GHRelease, newItem: GHRelease) =
        oldItem == newItem
}
