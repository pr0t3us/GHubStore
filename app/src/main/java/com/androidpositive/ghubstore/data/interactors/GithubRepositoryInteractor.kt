package com.androidpositive.ghubstore.data.interactors

import com.androidpositive.ghubstore.di.IoDispatcher
import dagger.hilt.android.components.ViewModelComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.kohsuke.github.GHRelease
import org.kohsuke.github.GHRepository
import org.kohsuke.github.GitHub
import org.kohsuke.github.PagedIterable
import javax.inject.Inject

interface GithubRepositoryInteractor {
    suspend fun getRepository(name: String): Result<GHRepository>
    suspend fun getRepositories(names: List<String>): Result<List<GHRepository>>
    suspend fun listReleases(repository: GHRepository): Result<PagedIterable<GHRelease>>
}

@BoundTo(supertype = GithubRepositoryInteractor::class, component = ViewModelComponent::class)
class GithubRepositoryInteractorImpl @Inject constructor(
    private val githubClient: GitHub,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : GithubRepositoryInteractor {
    override suspend fun getRepository(name: String): Result<GHRepository> {
        return withContext(ioDispatcher) {
            return@withContext runCatching { githubClient.getRepository(name) }
        }
    }

    override suspend fun getRepositories(names: List<String>): Result<List<GHRepository>> {
        return withContext(ioDispatcher) {
            return@withContext runCatching { names.map { githubClient.getRepository(it) } }
        }
    }

    override suspend fun listReleases(repository: GHRepository): Result<PagedIterable<GHRelease>> {
        return withContext(ioDispatcher) {
            return@withContext runCatching { repository.listReleases() }
        }
    }
}
