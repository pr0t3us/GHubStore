package com.androidpositive.ghubstore.data.repository

import com.androidpositive.ghubstore.data.datasource.GithubRemoteDataSource
import dagger.hilt.android.components.ViewModelComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import org.kohsuke.github.GHRelease
import org.kohsuke.github.GHRepository
import javax.inject.Inject

interface GithubRepository {
    suspend fun fetchRepositories(names: List<String>): Result<List<GHRepository>>
    suspend fun fetchReleases(repository: GHRepository): Result<List<GHRelease>>
    suspend fun searchRepositories(name: String): Result<List<GHRepository>>
}

@BoundTo(supertype = GithubRepository::class, component = ViewModelComponent::class)
private class GithubRepositoryImpl @Inject constructor(
    private val remoteDataSource: GithubRemoteDataSource
) : GithubRepository {

    override suspend fun fetchRepositories(names: List<String>): Result<List<GHRepository>> {
        return runCatching { remoteDataSource.fetchRepositories(names) }
    }

    override suspend fun fetchReleases(repository: GHRepository): Result<List<GHRelease>> {
        return runCatching { remoteDataSource.fetchReleases(repository) }
    }

    override suspend fun searchRepositories(name: String): Result<List<GHRepository>> {
        return runCatching { remoteDataSource.searchRepositories(name) }
    }
}
