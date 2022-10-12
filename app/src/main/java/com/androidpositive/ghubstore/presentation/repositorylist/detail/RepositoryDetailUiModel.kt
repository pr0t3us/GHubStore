package com.androidpositive.ghubstore.presentation.repositorylist.detail

import org.kohsuke.github.GHRelease
import java.io.Serializable

data class RepositoryDetailUiModel(
    val id: Long,
    val name: String,
    val description: String,
    val releases: List<GHRelease>
) : Serializable
