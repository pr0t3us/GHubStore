package com.androidpositive.ghubstore.data.datasource

import com.androidpositive.ghubstore.di.IoDispatcher
import dagger.hilt.android.components.ViewModelComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.kohsuke.github.GHRelease
import org.kohsuke.github.GHRepository
import org.kohsuke.github.GitHub
import javax.inject.Inject

interface GithubRemoteDataSource {
    suspend fun fetchRepositories(names: List<String>): List<GHRepository>
    suspend fun fetchReleases(repository: GHRepository): List<GHRelease>
}

@BoundTo(supertype = GithubRemoteDataSource::class, component = ViewModelComponent::class)
private class GithubRemoteDataSourceImpl @Inject constructor(
    private val githubClient: GitHub,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : GithubRemoteDataSource {

    override suspend fun fetchRepositories(names: List<String>): List<GHRepository> {
        return withContext(dispatcher) {
            return@withContext names.map { githubClient.getRepository(it) }
        }
    }

    override suspend fun fetchReleases(repository: GHRepository): List<GHRelease> {
        return withContext(dispatcher) {
            return@withContext repository.listReleases().toList()
        }
    }
}
