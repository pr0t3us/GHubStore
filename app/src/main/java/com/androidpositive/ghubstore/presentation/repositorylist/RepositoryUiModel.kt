package com.androidpositive.ghubstore.presentation.repositorylist

import org.kohsuke.github.GHRepository
import java.io.Serializable

data class RepositoryUiModel(
    val id: Long,
    val name: String,
    val description: String,
    @Deprecated(message = "For testing purpose only")
    val rawData: GHRepository
) : Serializable
