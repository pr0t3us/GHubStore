package com.androidpositive.ghubstore.presentation.repositorylist

import androidx.lifecycle.viewModelScope
import com.androidpositive.viewmodel.ObservableViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryListViewModel @Inject constructor(
    private val interactor: RepositoryListInteractor
) : ObservableViewModel() {
    private val repositoryName: String = "pr0t3us/GHubStore"

    init {
        viewModelScope.launch {
            val repository = interactor.getRepository(repositoryName)
            val releases = interactor.listReleases(repository.getOrThrow())
        }
    }
}
