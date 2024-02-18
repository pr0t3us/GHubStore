package com.androidpositive.ghubstore.data.repository

import com.androidpositive.ghubstore.data.datasource.GithubRemoteDataSource
import com.androidpositive.ghubstore.di.DefaultDispatcher
import dagger.hilt.android.components.ViewModelComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.kohsuke.github.GHAsset
import org.kohsuke.github.GHRepository
import javax.inject.Inject

interface GithubRepository {
    suspend fun fetchRepositories(names: List<String>): Result<List<GHRepository>>
    suspend fun fetchReleases(repository: GHRepository): Result<LinkedHashMap<String, MutableList<List<GHAsset>>>>
    suspend fun searchRepositories(name: String): Result<List<GHRepository>>
}

@BoundTo(supertype = GithubRepository::class, component = ViewModelComponent::class)
class GithubRepositoryImpl @Inject constructor(
    private val remoteDataSource: GithubRemoteDataSource,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : GithubRepository {

    override suspend fun fetchRepositories(names: List<String>): Result<List<GHRepository>> {
        return runCatching { remoteDataSource.fetchRepositories(names) }
    }

    override suspend fun fetchReleases(
        repository: GHRepository
    ): Result<LinkedHashMap<String, MutableList<List<GHAsset>>>> {
        return withContext(dispatcher) {
            return@withContext runCatching {
                val groupedAssets = LinkedHashMap<String, MutableList<List<GHAsset>>>()
                remoteDataSource.fetchReleases(repository)
                    .groupByTo(groupedAssets, { it.name }, { it.listAssets().toList() })
                groupedAssets
            }
        }
    }

    override suspend fun searchRepositories(name: String): Result<List<GHRepository>> {
        return runCatching { remoteDataSource.searchRepositories(name) }
    }
}
