package com.androidpositive.ghubstore.ui.github.detail

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.asFlow
import com.androidpositive.ghubstore.R
import com.androidpositive.ghubstore.ui.github.RepositoryUiModel
import com.androidpositive.ghubstore.ui.github.ui.theme.GHubStoreTheme
import com.androidpositive.viewmodel.Resource
import kotlinx.coroutines.launch
import org.kohsuke.github.GHAsset

@Composable
fun RepositoryDetailPane(
    viewModel: RepositoryDetailViewModel = hiltViewModel<RepositoryDetailViewModelImpl>(),
    model: RepositoryUiModel
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val detailsState by viewModel.detailsState.asFlow().collectAsState(
        initial = Resource.Loading()
    )
    LaunchedEffect(key1 = model) {
        model.rawData?.let { viewModel.onInitScreen(it) }
    }
    GHubStoreTheme {
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }
        ) { paddingValues ->
            val errorMessage = stringResource(id = R.string.general_error)
            RepositoryDetailPaneInner(
                modifier = Modifier.padding(paddingValues),
                detailsState = detailsState,
                onItemClick = viewModel::onAssetClick,
                onError = {
                    scope.launch {
                        snackbarHostState.showSnackbar(errorMessage)
                    }
                }
            )
        }
    }
}

@Composable
fun RepositoryDetailPaneInner(
    modifier: Modifier,
    detailsState: Resource<LinkedHashMap<String, MutableList<List<GHAsset>>>>,
    onItemClick: (GHAsset) -> Unit,
    onError: () -> Unit
) {
    when (detailsState) {
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
                items(detailsState.data.keys.toList()) { release ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(4.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = release,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        detailsState.data[release]?.flatten()?.onEach { asset ->
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .clickable(onClick = { onItemClick(asset) }),
                                text = asset.name,
                                style = MaterialTheme.typography.titleSmall,
                                textAlign = TextAlign.End
                            )
                            Spacer(modifier = Modifier.height(2.dp))
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
fun RepositoryDetailPaneInnerPreview() {
    GHubStoreTheme {
        RepositoryDetailPaneInner(
            modifier = Modifier,
            detailsState = previewSuccessData,
            onItemClick = {},
            onError = {}
        )
    }
}

private val previewSuccessData = Resource.Success(
    linkedMapOf(
        "Repository 1" to mutableListOf<List<GHAsset>>(),
        "Repository 2" to mutableListOf<List<GHAsset>>(),
        "Repository 3" to mutableListOf<List<GHAsset>>(),
    )
)
