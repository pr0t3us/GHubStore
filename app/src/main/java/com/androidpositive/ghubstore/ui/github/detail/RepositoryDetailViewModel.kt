package com.androidpositive.ghubstore.ui.github.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidpositive.ghubstore.data.FileDownloader
import com.androidpositive.ghubstore.data.repository.GithubRepository
import com.androidpositive.viewmodel.Resource
import com.androidpositive.viewmodel.toResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.kohsuke.github.GHAsset
import org.kohsuke.github.GHRepository
import javax.inject.Inject

interface RepositoryDetailViewModel {
    val detailsState: LiveData<Resource<LinkedHashMap<String, MutableList<List<GHAsset>>>>>
    fun onInitScreen(selectedRepository: GHRepository)
    fun onAssetClick(asset: GHAsset)
}

@HiltViewModel
class RepositoryDetailViewModelImpl @Inject constructor(
    private val fileDownloader: FileDownloader,
    private val repository: GithubRepository
) : ViewModel(), RepositoryDetailViewModel {
    override val detailsState: MutableLiveData<Resource<LinkedHashMap<String, MutableList<List<GHAsset>>>>> =
        MutableLiveData(Resource.Loading())

    override fun onInitScreen(selectedRepository: GHRepository) {
        detailsState.value = Resource.Loading()
        viewModelScope.launch {
            detailsState.value = repository.fetchReleases(selectedRepository).toResource()
        }
    }

    override fun onAssetClick(asset: GHAsset) {
        fileDownloader.downloadFile(asset.browserDownloadUrl, asset.name)
    }

}
