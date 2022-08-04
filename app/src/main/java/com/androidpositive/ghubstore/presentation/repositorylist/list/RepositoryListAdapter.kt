package com.androidpositive.ghubstore.presentation.repositorylist.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.androidpositive.ghubstore.R
import com.androidpositive.ghubstore.databinding.RepositoryListContentBinding
import com.androidpositive.ghubstore.presentation.repositorylist.detail.RepositoryDetailFragment
import com.androidpositive.ghubstore.presentation.repositorylist.list.RepositoryListAdapter.ViewHolder
import org.kohsuke.github.GHRepository

class RepositoryListAdapter(
    private val itemDetailFragmentContainer: View?,
    _repositories: List<GHRepository> = emptyList()
) : ListAdapter<GHRepository, ViewHolder>(callback) {
    companion object {
        val callback = object : DiffUtil.ItemCallback<GHRepository>() {
            override fun areItemsTheSame(oldItem: GHRepository, newItem: GHRepository) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: GHRepository, newItem: GHRepository) =
                oldItem == newItem
        }
    }

    var list: List<GHRepository>
        get() = currentList
        set(value) {
            submitList(value)
        }

    init {
        list = _repositories
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RepositoryListContentBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))

        with(holder.itemView) {
            tag = getItem(position)
            setOnClickListener { itemView ->
                navigateToRepositoryDetails(itemView, itemDetailFragmentContainer)
            }
        }
    }

    private fun navigateToRepositoryDetails(
        itemView: View,
        itemDetailFragmentContainer: View?
    ) {
        val repository = itemView.tag as GHRepository
        val bundle = Bundle()
        bundle.putLong(
            RepositoryDetailFragment.ARG_ITEM_ID,
            repository.id
        )
        if (itemDetailFragmentContainer != null) {
            itemDetailFragmentContainer.findNavController()
                .navigate(R.id.fragment_repository_detail, bundle)
        } else {
            itemView.findNavController().navigate(R.id.show_repository_detail, bundle)
        }
    }

    inner class ViewHolder(
        private val binding: RepositoryListContentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GHRepository) {
            with(binding) {
                idText.text = item.name
                content.text = item.description
            }
        }
    }
}
