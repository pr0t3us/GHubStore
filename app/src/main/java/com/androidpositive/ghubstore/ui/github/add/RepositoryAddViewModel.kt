package com.androidpositive.ghubstore.ui.github.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidpositive.ghubstore.data.repository.GithubRepository
import com.androidpositive.ghubstore.data.repository.SourceListRepository
import com.androidpositive.ghubstore.domain.ValidateRepositoryNameUseCase
import com.androidpositive.ghubstore.ui.github.RepositoryUiModel
import com.androidpositive.ghubstore.ui.github.list.toUiModels
import com.androidpositive.viewmodel.Resource
import com.androidpositive.viewmodel.Resource.Loading
import com.androidpositive.viewmodel.toResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

interface RepositoryAddViewModel {
    val repositories: LiveData<Resource<List<RepositoryUiModel>>>

    fun onSearchRepository(name: String)
    fun onCtaClicked(name: String)
}

@HiltViewModel
class RepositoryAddViewModelImpl @Inject constructor(
    private val sourceListRepository: SourceListRepository,
    private val repository: GithubRepository,
    private val validateRepository: ValidateRepositoryNameUseCase
) : ViewModel(), RepositoryAddViewModel {
    override val repositories = MutableLiveData<Resource<List<RepositoryUiModel>>>(Loading())
    private var isRepositoryFullNameValid = false

    override fun onSearchRepository(name: String) {
        viewModelScope.launch {
            isRepositoryFullNameValid = validateRepository(name)
            val repositoryListRawData = repository.searchRepositories(name)
            repositories.value = repositoryListRawData.toUiModels().toResource()
        }
    }

    override fun onCtaClicked(name: String) {
        if (isRepositoryFullNameValid) {
            viewModelScope.launch {
                sourceListRepository.saveSource(name)
            }
        }
    }
}
