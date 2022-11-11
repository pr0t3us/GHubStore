package com.androidpositive.ghubstore.ui.github.list

import androidx.navigation.NavController
import com.androidpositive.ghubstore.ui.github.detail.RepositoryDetailUiModel

data class RepositoryDetailNavigationModel(
    val navController: NavController,
    val repositoryDetailUiModel: RepositoryDetailUiModel
)
