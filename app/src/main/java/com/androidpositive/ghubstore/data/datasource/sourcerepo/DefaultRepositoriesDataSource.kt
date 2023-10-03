package com.androidpositive.ghubstore.data.datasource.sourcerepo

import android.content.Context
import com.androidpositive.ghubstore.R
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DefaultRepositoriesDataSource(
    private val context: Context,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun fetchDefaultSources(): List<String> = withContext(dispatcher) {
        context.resources.getStringArray(R.array.default_sources).asList()
    }
}
