package com.androidpositive.ghubstore.ui.github.detail

import androidx.lifecycle.ViewModel
import com.androidpositive.ghubstore.data.FileDownloader
import dagger.hilt.android.lifecycle.HiltViewModel
import org.kohsuke.github.GHAsset
import javax.inject.Inject

interface RepositoryDetailViewModel {
    fun onAssetClick(asset: GHAsset)
}

@HiltViewModel
class RepositoryDetailViewModelImpl @Inject constructor(
    private val fileDownloader: FileDownloader
): ViewModel(), RepositoryDetailViewModel {

    override fun onAssetClick(asset: GHAsset) {
        fileDownloader.downloadFile(asset.browserDownloadUrl, asset.name)
    }
}
