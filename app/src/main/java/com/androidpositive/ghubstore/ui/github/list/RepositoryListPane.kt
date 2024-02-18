package com.androidpositive.ghubstore.ui.github.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.asFlow
import com.androidpositive.ghubstore.R
import com.androidpositive.ghubstore.ui.github.RepositoryUiModel
import com.androidpositive.ghubstore.ui.github.add.RepositoryAddSheet
import com.androidpositive.ghubstore.ui.github.ui.theme.GHubStoreTheme
import com.androidpositive.viewmodel.Resource
import kotlinx.coroutines.launch

@Composable
fun RepositoryListPane(
    viewModel: RepositoryListViewModel = hiltViewModel<RepositoryListViewModelImpl>(),
    onItemClick: (RepositoryUiModel) -> Unit
) {

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val repositoryListState by viewModel.repositories.asFlow().collectAsState(
        initial = Resource.Loading()
    )
    var showBottomSheet by remember { mutableStateOf(false) }
    GHubStoreTheme {
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            floatingActionButton = {
                FloatingActionButton(
                    modifier = Modifier.wrapContentSize(),
                    onClick = { showBottomSheet = true },
                ) {
                    Icon(
                        Icons.Filled.Add,
                        stringResource(id = R.string.repository_add_cta_description)
                    )
                }
            }
        ) { paddingValues ->
            val errorMessage = stringResource(id = R.string.general_error)
            RepositoryListPaneInner(
                modifier = Modifier.padding(paddingValues),
                repositoryListState = repositoryListState,
                onItemClick = onItemClick,
                onError = {
                    scope.launch {
                        snackbarHostState.showSnackbar(errorMessage)
                    }
                }
            )
            if (showBottomSheet) {
                RepositoryAddSheet(
                    onNavigateBack = {
                        showBottomSheet = false
                    }
                )
            }
        }
    }
}

@Composable
fun RepositoryListPaneInner(
    modifier: Modifier,
    repositoryListState: Resource<List<RepositoryUiModel>>,
    onItemClick: (RepositoryUiModel) -> Unit,
    onError: () -> Unit
) {
    when (repositoryListState) {
        is Resource.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = modifier.width(64.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
        }

        is Resource.Success -> {
            LazyColumn(
                modifier = modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(repositoryListState.data) { repository ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(4.dp)
                            .clickable(onClick = { onItemClick(repository) }),
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = repository.name,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        repository.description?.let { description ->
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = description,
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                    }
                }
            }
        }

        is Resource.Failure -> {
            onError()
        }
    }
}

@PreviewLightDark
@Composable
fun RepositoryListPaneInnerPreview() {
    GHubStoreTheme {
        RepositoryListPaneInner(
            modifier = Modifier,
            repositoryListState = previewSuccessData,
            onItemClick = {},
            onError = {}
        )
    }
}

private val previewSuccessData = Resource.Success(
    listOf(
        RepositoryUiModel(
            id = 1,
            name = "Test 1",
            description = "Test 1 description"
        ),
        RepositoryUiModel(
            id = 2,
            name = "Test 2",
            description = "Test 2 description"
        )
    )
)
