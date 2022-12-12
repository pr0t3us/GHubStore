package com.androidpositive.ghubstore.data.repository

import com.androidpositive.ghubstore.data.datasource.sourcelist.Source
import com.androidpositive.ghubstore.data.datasource.sourcelist.SourceDao
import dagger.hilt.android.components.ViewModelComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import javax.inject.Inject

interface SourceListRepository {
    suspend fun fetchSources(): Result<List<Source>>
}

@BoundTo(supertype = SourceListRepository::class, component = ViewModelComponent::class)
private class SourceListRepositoryImpl @Inject constructor(
    private val sourceDao: SourceDao
) : SourceListRepository {

    override suspend fun fetchSources(): Result<List<Source>> {
        return runCatching { sourceDao.getAll() }
    }
}
