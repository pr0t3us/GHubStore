package com.androidpositive.ghubstore.data.datasource.sourcerepo

import android.content.Context
import com.androidpositive.ghubstore.R.array

class DefaultSourcesProvider(private val context: Context) {
    val sources: List<String>
        get() = context.resources.getStringArray(array.default_sources).asList()
}
