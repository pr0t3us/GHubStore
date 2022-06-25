package com.androidpositive.ghubstore.presentation.home

import com.androidpositive.viewmodel.ObservableViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.kohsuke.github.GitHub
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val githubClient: GitHub
) : ObservableViewModel() {

}
