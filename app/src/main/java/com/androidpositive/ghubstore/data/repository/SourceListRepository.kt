package com.androidpositive.ghubstore.data.repository

import com.androidpositive.ghubstore.data.datasource.Mapper
import com.androidpositive.ghubstore.data.datasource.sourcerepo.DefaultRepositoriesDataSource
import com.androidpositive.ghubstore.data.datasource.sourcerepo.SourceDao
import com.androidpositive.ghubstore.data.datasource.sourcerepo.SourceDto
import com.androidpositive.ghubstore.data.datasource.sourcerepo.SourceEntity
import dagger.hilt.android.components.ViewModelComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import javax.inject.Inject

interface SourceListRepository {
    suspend fun fetchDefaultSources(): Result<List<SourceDto>>
    suspend fun fetchSources(): Result<List<SourceDto>>
    suspend fun saveSource(name: String)
}

@BoundTo(supertype = SourceListRepository::class, component = ViewModelComponent::class)
class SourceListRepositoryImpl @Inject constructor(
    private val sourceDao: SourceDao,
    private val defaultRepositories: DefaultRepositoriesDataSource,
    private val sourceMapper: Mapper<SourceEntity, SourceDto>
) : SourceListRepository {

    override suspend fun fetchDefaultSources(): Result<List<SourceDto>> {
        return runCatching { defaultRepositories.fetchDefaultSources().map { SourceDto(it) } }
    }

    override suspend fun fetchSources(): Result<List<SourceDto>> {
        return runCatching {
            sourceDao.getAll().map { sourceMapper.convertToTarget(it) }
        }
    }

    override suspend fun saveSource(name: String) {
        runCatching {
            sourceDao.insertAll(sourceMapper.convertToSource(SourceDto(name)))
        }
    }
}
