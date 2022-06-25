package com.androidpositive.ghubstore.presentation.repositorylist

import com.androidpositive.viewmodel.ObservableViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.kohsuke.github.GitHub
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val githubClient: GitHub
) : ObservableViewModel() {
    private val repositoryName: String = "pr0t3us/GHubStore"
    init {
        val repository = githubClient.getRepository(repositoryName)
        val releases = repository.listReleases()

    }
}
