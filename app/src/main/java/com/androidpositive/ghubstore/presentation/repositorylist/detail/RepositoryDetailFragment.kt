package com.androidpositive.ghubstore.presentation.repositorylist.detail

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.androidpositive.ghubstore.R
import com.androidpositive.ghubstore.databinding.FragmentRepositoryDetailBinding
import com.androidpositive.ghubstore.presentation.placeholder.PlaceholderContent
import com.androidpositive.viewbinding.viewBinding
import com.google.android.material.appbar.CollapsingToolbarLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepositoryDetailFragment : Fragment(R.layout.fragment_repository_detail) {
    private val binding by viewBinding(FragmentRepositoryDetailBinding::bind)
    /**
     * The placeholder content this fragment is presenting.
     */
    private var item: PlaceholderContent.PlaceholderItem? = null

    lateinit var itemDetailTextView: TextView
    private var toolbarLayout: CollapsingToolbarLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // Load the placeholder content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                item = PlaceholderContent.ITEM_MAP[it.getString(ARG_ITEM_ID)]
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarLayout = binding.toolbarLayout
        itemDetailTextView = binding.repositoryDetail

        updateContent()
    }

    private fun updateContent() {
        toolbarLayout?.title = item?.content

        // Show the placeholder content as text in a TextView.
        item?.let {
            itemDetailTextView.text = it.details
        }
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}
