package com.androidpositive.ghubstore.ui.github.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.androidpositive.ghubstore.data.repository.GithubRepository
import com.androidpositive.ghubstore.data.repository.SourceListRepository
import com.androidpositive.ghubstore.ui.github.RepositoryUiModel
import com.androidpositive.viewmodel.Resource
import com.androidpositive.viewmodel.toResource
import dagger.hilt.android.lifecycle.HiltViewModel
import org.kohsuke.github.GHRepository
import javax.inject.Inject

interface RepositoryListViewModel {
    val repositories: LiveData<Resource<List<RepositoryUiModel>>>
}

@HiltViewModel
class RepositoryListViewModelImpl @Inject constructor(
    private val sourceListRepository: SourceListRepository,
    private val repository: GithubRepository
) : ViewModel(), RepositoryListViewModel {
    private var repositoryListRawData: Result<List<GHRepository>>? = null

    override val repositories: LiveData<Resource<List<RepositoryUiModel>>> = liveData {
        emit(Resource.Loading())
        try {
            val defaultSources = sourceListRepository.fetchDefaultSources().getOrThrow()
            val localSources = sourceListRepository.fetchSources().getOrThrow()
            val mergedSources = (defaultSources + localSources).map { it.name }
            repositoryListRawData = repository.fetchRepositories(mergedSources)
            val repositories = repositoryListRawData!!.toUiModels().toResource()
            emit(repositories)
        } catch (exception: Exception) {
            emit(Resource.Failure(exception))
        }
    }
}

fun Result<List<GHRepository>>.toUiModels(): Result<List<RepositoryUiModel>> {
    return map { it.map { ghRepository -> ghRepository.toRepositoryUiModel() } }
}

fun GHRepository.toRepositoryUiModel() = RepositoryUiModel(id, name, description, this)
