package com.androidpositive.ghubstore.ui.github.list

import com.androidpositive.extensions.createCaptor
import com.androidpositive.ghubstore.data.repository.GithubRepository
import com.androidpositive.ghubstore.data.repository.SourceListRepository
import com.androidpositive.viewmodel.BaseViewModelTest
import com.androidpositive.viewmodel.Resource
import com.androidpositive.viewmodel.toResource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.kohsuke.github.GHRepository
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class RepositoryListViewModelTest : BaseViewModelTest() {
    private val interactor = mock<GithubRepository>()
    private val sourceListRepository = mock<SourceListRepository>()

    @Test
    fun `sets successful state when get the repository list`() = runTest {
        whenever(sourceListRepository.fetchDefaultSources()).thenReturn(Result.success(emptyList()))
        whenever(sourceListRepository.fetchSources()).thenReturn(Result.success(emptyList()))
        val repositories = Result.success(listOf<GHRepository>())
        whenever(interactor.fetchRepositories(any())).thenReturn(repositories)

        val viewModel = RepositoryListViewModelImpl(sourceListRepository, interactor)
        val repositoriesCaptor = viewModel.repositories.createCaptor()

        repositoriesCaptor.assertSendsValues(
            Resource.Loading(),
            repositories.toUiModels().toResource()
        )
    }

    @Test
    fun `sets failure state when get the repository list`() = runTest {
        whenever(sourceListRepository.fetchDefaultSources()).thenReturn(Result.success(emptyList()))
        whenever(sourceListRepository.fetchSources()).thenReturn(Result.success(emptyList()))
        val error = Result.failure<List<GHRepository>>(Throwable())
        whenever(interactor.fetchRepositories(any())).thenReturn(error)

        val viewModel = RepositoryListViewModelImpl(sourceListRepository, interactor)
        val repositoriesCaptor = viewModel.repositories.createCaptor()

        repositoriesCaptor.assertSendsValues(
            Resource.Loading(),
            error.toUiModels().toResource()
        )
    }
}
