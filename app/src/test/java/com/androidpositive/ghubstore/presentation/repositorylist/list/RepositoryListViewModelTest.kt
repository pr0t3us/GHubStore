package com.androidpositive.ghubstore.presentation.repositorylist.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.androidpositive.extensions.MainCoroutineScopeRule
import com.androidpositive.extensions.createCaptor
import com.androidpositive.ghubstore.data.interactors.GithubRepositoryInteractor
import com.androidpositive.viewmodel.Resource
import com.androidpositive.viewmodel.toResource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.kohsuke.github.GHRepository
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class RepositoryListViewModelTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val coroutineScope = MainCoroutineScopeRule()

    private val interactor = mock<GithubRepositoryInteractor>()

    @Test
    fun `sets successful state when get the repository list`() = runTest {
        val repositories = Result.success(listOf<GHRepository>())
        whenever(interactor.getRepositories(any())).thenReturn(repositories)

        val viewModel = RepositoryListViewModel(interactor)
        val repositoriesCaptor = viewModel.repositories.createCaptor()

        repositoriesCaptor.assertSendsValues(
            Resource.Loading(),
            repositories.toUiModels().toResource()
        )
    }

    @Test
    fun `sets failure state when get the repository list`() = runTest {
        val error = Result.failure<List<GHRepository>>(Throwable())
        whenever(interactor.getRepositories(any())).thenReturn(error)

        val viewModel = RepositoryListViewModel(interactor)
        val repositoriesCaptor = viewModel.repositories.createCaptor()

        repositoriesCaptor.assertSendsValues(
            Resource.Loading(),
            error.toUiModels().toResource()
        )
    }
}
