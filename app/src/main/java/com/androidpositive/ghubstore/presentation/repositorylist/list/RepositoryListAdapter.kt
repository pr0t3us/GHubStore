package com.androidpositive.ghubstore.presentation.repositorylist.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.androidpositive.ghubstore.R
import com.androidpositive.ghubstore.databinding.RepositoryListContentBinding
import com.androidpositive.ghubstore.presentation.repositorylist.detail.RepositoryDetailFragment
import com.androidpositive.ghubstore.presentation.repositorylist.list.RepositoryListAdapter.ViewHolder
import org.kohsuke.github.GHRepository

class RepositoryListAdapter(
    private val repositories: List<GHRepository>,
    private val itemDetailFragmentContainer: View?
) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RepositoryListContentBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = repositories[position]
        holder.idView.text = item.name
        holder.contentView.text = item.description

        with(holder.itemView) {
            tag = item
            setOnClickListener { itemView ->
                navigateToRepositoryDetails(itemView, itemDetailFragmentContainer)
            }
        }
    }

    override fun getItemCount() = repositories.size

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
        binding: RepositoryListContentBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.idText
        val contentView: TextView = binding.content
    }
}
