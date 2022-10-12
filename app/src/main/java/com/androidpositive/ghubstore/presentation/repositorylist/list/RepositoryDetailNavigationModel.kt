package com.androidpositive.ghubstore.presentation.repositorylist.list

import androidx.navigation.NavController
import com.androidpositive.ghubstore.presentation.repositorylist.detail.RepositoryDetailUiModel

data class RepositoryDetailNavigationModel(
    val navController: NavController,
    val repositoryDetailUiModel: RepositoryDetailUiModel
)
