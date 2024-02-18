package com.androidpositive.ghubstore.ui.github.add

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import com.androidpositive.ghubstore.ui.github.ui.theme.GHubStoreTheme
import com.androidpositive.viewmodel.Resource
import kotlinx.coroutines.launch

@Composable
fun RepositoryAddSheet(
    viewModel: RepositoryAddViewModel = hiltViewModel<RepositoryAddViewModelImpl>(),
    onNavigateBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val repositoriesState by viewModel.repositories.asFlow().collectAsState(
        initial = Resource.Loading()
    )

    val errorMessage = stringResource(id = R.string.general_error)
    RepositoryAddSheetInner(
        modifier = Modifier,
        repositoriesState = repositoriesState,
        onItemClick = viewModel::onCtaClicked,
        onDismissRequest = onNavigateBack,
        onError = {
            scope.launch {
                snackbarHostState.showSnackbar(errorMessage)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoryAddSheetInner(
    modifier: Modifier,
    repositoriesState: Resource<List<RepositoryUiModel>>,
    onItemClick: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onError: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = {
            onDismissRequest()
        },
        sheetState = sheetState
    ) {
        fun hideBottomSheet() {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) {
                    onDismissRequest()
                }
            }
        }
        BackHandler {
            hideBottomSheet()
        }
        var selectedRepository by remember { mutableStateOf("") }

        Row(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                modifier = Modifier.padding(4.dp).weight(1f),
                value = selectedRepository,
                onValueChange = { selectedRepository = it },
                label = { Text(stringResource(id = R.string.repository_add_full_name_hint)) }
            )
            Button(
                modifier = Modifier.padding(4.dp),
                onClick = {
                    onItemClick(selectedRepository)
                    hideBottomSheet()
                }
            ) {
                Icon(Icons.Filled.Add, stringResource(id = R.string.repository_add_cta_description))
            }

        }
        when (repositoriesState) {
            is Resource.Loading -> {
                Box(
                    modifier = modifier.fillMaxSize(),
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
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(repositoriesState.data) { repository ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(4.dp)
                                .clickable(onClick = { selectedRepository = repository.name }),
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
}

@PreviewLightDark
@Composable
fun RepositoryAddSheetInnerPreview() {
    GHubStoreTheme {
        RepositoryAddSheetInner(
            modifier = Modifier.fillMaxWidth(),
            repositoriesState = previewSuccessData,
            onItemClick = {},
            onDismissRequest = { },
            onError = { }
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
