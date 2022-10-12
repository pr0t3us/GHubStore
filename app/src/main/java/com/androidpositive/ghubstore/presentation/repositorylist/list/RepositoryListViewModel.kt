package com.androidpositive.ghubstore.presentation.repositorylist.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.androidpositive.ghubstore.data.interactors.GithubRepositoryInteractor
import com.androidpositive.ghubstore.presentation.repositorylist.RepositoryUiModel
import com.androidpositive.ghubstore.presentation.repositorylist.detail.RepositoryDetailUiModel
import com.androidpositive.viewmodel.Event
import com.androidpositive.viewmodel.Resource
import com.androidpositive.viewmodel.toResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.kohsuke.github.GHRelease
import org.kohsuke.github.GHRepository
import javax.inject.Inject

interface RepositoryListViewModel {
    val repositories: LiveData<Resource<List<RepositoryUiModel>>>
    val detailsNavigationUiModel: LiveData<Event<Resource<RepositoryDetailNavigationModel>?>>
    fun onItemClicked(position: Int, navController: NavController)
}

@HiltViewModel
class RepositoryListViewModelImpl @Inject constructor(
    private val interactor: GithubRepositoryInteractor
) : ViewModel(), RepositoryListViewModel {
    private val repositoryNameList: List<String> = listOf("pr0t3us/GHubStore")
    private var repositoryListRawData: Result<List<GHRepository>>? = null

    override val repositories: LiveData<Resource<List<RepositoryUiModel>>> = liveData {
        emit(Resource.Loading())
        try {
            repositoryListRawData = interactor.getRepositories(repositoryNameList)
            val repositories = repositoryListRawData!!.toUiModels().toResource()
            emit(repositories)
        } catch (exception: Exception) {
            emit(Resource.Failure(exception))
        }
    }
    override val detailsNavigationUiModel =
        MutableLiveData<Event<Resource<RepositoryDetailNavigationModel>?>>()

    override fun onItemClicked(position: Int, navController: NavController) {
        viewModelScope.launch {
            detailsNavigationUiModel.value = Event(
                repositoryListRawData?.map {
                    val repository = it[position]
                    interactor.listReleases(repository).toUiModel(repository, navController)
                }?.toResource()
            )
        }
    }
}

fun Result<List<GHRelease>>.toUiModel(
    repository: GHRepository,
    navController: NavController
): RepositoryDetailNavigationModel = RepositoryDetailNavigationModel(
    navController = navController,
    repositoryDetailUiModel = RepositoryDetailUiModel(
        id = repository.id,
        name = repository.name,
        description = repository.description,
        releases = getOrDefault(emptyList())
    )
)

fun Result<List<GHRepository>>.toUiModels(): Result<List<RepositoryUiModel>> {
    return map { it.map { ghRepository -> ghRepository.toRepositoryUiModel() } }
}

fun GHRepository.toRepositoryUiModel() = RepositoryUiModel(id, name, description, this)
