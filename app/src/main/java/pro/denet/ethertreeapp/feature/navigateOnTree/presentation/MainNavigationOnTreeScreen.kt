package pro.denet.ethertreeapp.feature.navigateOnTree.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.PersistentList
import pro.denet.ethertreeapp.R
import pro.denet.ethertreeapp.core.designsystem.icon.TreeAppIcon
import pro.denet.ethertreeapp.core.designsystem.theme.TreeAppTheme
import pro.denet.ethertreeapp.core.sl.locate
import pro.denet.ethertreeapp.core.widget.DenetFloatingActionButton
import pro.denet.ethertreeapp.core.widget.DenetMainNodeCard
import pro.denet.ethertreeapp.core.widget.DenetNodeCard
import pro.denet.ethertreeapp.core.widget.DenetProgressBar
import pro.denet.ethertreeapp.core.widget.DenetSecondaryButton
import pro.denet.ethertreeapp.core.widget.DenetSnackbar
import pro.denet.ethertreeapp.core.widget.DenetTopBar
import pro.denet.ethertreeapp.core.widget.model.NodeUiModel
import pro.denet.ethertreeapp.feature.navigateOnTree.presentation.MainNavigationOnTreeViewModel.MainScreenAction
import pro.denet.ethertreeapp.feature.navigateOnTree.presentation.MainNavigationOnTreeViewModel.MainScreenEvent
import pro.denet.ethertreeapp.feature.navigateOnTree.presentation.MainNavigationOnTreeViewModel.MainScreenState

@Composable
fun MainNavigationOnTreeScreen() {

    val viewModel = locate<MainNavigationOnTreeViewModel>()
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    val screenAction by viewModel.screenAction.collectAsStateWithLifecycle(initialValue = null)

    val snackbarHostState = remember { SnackbarHostState() }

    MainScreenContent(
        screenState = screenState,
        eventHandler = viewModel::eventHandler,
        snackbarHostState = snackbarHostState
    )

    MainScreenActions(
        screenAction = screenAction,
        snackbarHostState = snackbarHostState
    )
}


@Composable
private fun MainScreenContent(
    screenState: MainScreenState,
    eventHandler: (MainScreenEvent) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    Scaffold(
        snackbarHost = {
            DenetSnackbar(snackbarHostState = snackbarHostState)
        },
        topBar = {
            DenetTopBar(
                title = stringResource(R.string.app_name)
            )
        },
        floatingActionButton = {
            DenetFloatingActionButton(
                icon = TreeAppIcon.Add
            ) {
                if (screenState.currentNode != null)
                    eventHandler(MainScreenEvent.OnAddNode(screenState.currentNode.id))
            }
        },
        containerColor = TreeAppTheme.treeAppColor.background,
    ) { contentPadding ->

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            color = TreeAppTheme.treeAppColor.background
        ) {
            when (screenState.currentNode == null || screenState.isLoading) {
                true -> {
                    DenetProgressBar(shouldShow = true)
                }

                false -> {
                    println("here")
                    if (screenState.isError) {
                        // TODO: Add Error Message
                        Text(
                            modifier = Modifier
                                .fillMaxSize(1f)
                                .padding(top = 300.dp),
                            text = "Error",
                            color = TreeAppTheme.treeAppColor.primaryText
                        )
                    } else {
                        println("q")
                        Column {
                            CurrentNode(
                                currentNode = screenState.currentNode,
                                onTrash = {
                                    eventHandler(
                                        MainScreenEvent
                                            .OnParentNodeTrashClick(idNode = it)
                                    )
                                },
                                toParent = {
                                    eventHandler(
                                        MainScreenEvent.OnNavigateToParentNode(
                                            idCurrentNode = it
                                        )
                                    )
                                }
                            )
                            NavigationOnChildrenContent(
                                childrenCurrentNode = screenState.childrenCurrentNode,
                                onClick = {
                                    eventHandler(
                                        MainScreenEvent
                                            .OnNavigateToChildNode(idNode = it)
                                    )
                                },
                                onTrash = {
                                    eventHandler(
                                        MainScreenEvent
                                            .OnNodeTrashClick(idNode = it)
                                    )
                                },
                            )
                        }
                    }
                }
            }
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavigationOnChildrenContent(
    childrenCurrentNode: PersistentList<NodeUiModel>,
    onTrash: (Int) -> Unit,
    onClick: (Int) -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(space = 12.dp),
        contentPadding = PaddingValues(all = 16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        items(
            items = childrenCurrentNode,
            key = { it.id },
        ) { node ->
            DenetNodeCard(
                modifier = Modifier.animateItemPlacement(),
                node = node,
                onTrash = { onTrash(node.id) },
                onClick = { onClick(node.id) }
            )
        }
    }
}

@Composable
fun CurrentNode(
    currentNode: NodeUiModel,
    onTrash: (Int) -> Unit,
    toParent: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .wrapContentHeight(),
    ) {
        DenetMainNodeCard(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp),
            node = currentNode,
            onTrashClick = { onTrash(currentNode.id) },
            onPress = {/* TODO: Add onTrash Click*/ }
        )
        DenetSecondaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = "To Parent",
            isActive = !currentNode.isRoot,
            noActiveText = "No Parent",
            icon = TreeAppIcon.ArrowUp,
            onClick = { toParent(currentNode.id) }
        )
    }
}

@Composable
private fun MainScreenActions(
    screenAction: MainScreenAction?,
    snackbarHostState: SnackbarHostState
) {

}