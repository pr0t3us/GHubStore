package com.androidpositive.ghubstore.ui.github

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.AnimatedPane
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.androidpositive.ghubstore.ui.github.detail.RepositoryDetailPane
import com.androidpositive.ghubstore.ui.github.list.RepositoryListPane
import com.androidpositive.ghubstore.ui.github.ui.theme.GHubStoreTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepositoryComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GHubStoreTheme {
                RepositoryListDetailScaffold()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GHubStoreTheme {
        Greeting("Android")
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun RepositoryListDetailScaffold() {
    val navigator = rememberListDetailPaneScaffoldNavigator<Nothing>()

    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }

    var selectedItem: RepositoryItem? by rememberSaveable(stateSaver = RepositoryItem.Saver) {
        mutableStateOf(null)
    }

    ListDetailPaneScaffold(
        scaffoldState = navigator.scaffoldState,
        listPane = {
            AnimatedPane(Modifier) {
                RepositoryListPane(
                    onItemClick = { repositoryItem ->
                        selectedItem = RepositoryItem(repositoryItem)
                        navigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                    }
                )
            }
        },
        detailPane = {
            selectedItem?.model?.let { repositoryUiModel ->
                AnimatedPane(Modifier) {
                    RepositoryDetailPane(model = repositoryUiModel)
                }
            }
        }
    )
}

private class RepositoryItem(val model: RepositoryUiModel) {
    companion object {
        val Saver: Saver<RepositoryItem?, RepositoryUiModel> = Saver(
            { it?.model },
            ::RepositoryItem,
        )
    }
}
