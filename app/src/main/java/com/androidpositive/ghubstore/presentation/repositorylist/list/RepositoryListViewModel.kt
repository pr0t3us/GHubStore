package com.androidpositive.ghubstore.presentation.repositorylist.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.androidpositive.ghubstore.data.interactors.GithubRepositoryInteractor
import com.androidpositive.ghubstore.presentation.repositorylist.RepositoryUiModel
import com.androidpositive.viewmodel.Resource
import com.androidpositive.viewmodel.toResource
import dagger.hilt.android.lifecycle.HiltViewModel
import org.kohsuke.github.GHRepository
import javax.inject.Inject

@HiltViewModel
class RepositoryListViewModel @Inject constructor(
    private val interactor: GithubRepositoryInteractor
) : ViewModel() {
    private val repositoryNameList: List<String> = listOf("pr0t3us/GHubStore")
    val repositories: LiveData<Resource<List<RepositoryUiModel>>> = liveData {
        emit(Resource.Loading())
        try {
            val repositories =
                interactor.getRepositories(repositoryNameList).toUiModels().toResource()
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
