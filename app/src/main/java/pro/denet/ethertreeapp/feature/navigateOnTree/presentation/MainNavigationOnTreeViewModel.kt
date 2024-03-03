package pro.denet.ethertreeapp.feature.navigateOnTree.presentation

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pro.denet.ethertreeapp.core.widget.model.NodeUiModel
import pro.denet.ethertreeapp.feature.navigateOnTree.domain.useCase.AddNodeToParentUseCase
import pro.denet.ethertreeapp.feature.navigateOnTree.domain.useCase.DeleteNodeUseCase
import pro.denet.ethertreeapp.feature.navigateOnTree.domain.useCase.GetNodeWithChildrenUseCase
import pro.denet.ethertreeapp.feature.navigateOnTree.domain.useCase.GetRootNodeUseCase
import pro.denet.ethertreeapp.feature.navigateOnTree.presentation.MainNavigationOnTreeViewModel.MainScreenEvent.OnAddNode
import pro.denet.ethertreeapp.feature.navigateOnTree.presentation.MainNavigationOnTreeViewModel.MainScreenEvent.OnInit
import pro.denet.ethertreeapp.feature.navigateOnTree.presentation.MainNavigationOnTreeViewModel.MainScreenEvent.OnNavigateToChildNode
import pro.denet.ethertreeapp.feature.navigateOnTree.presentation.MainNavigationOnTreeViewModel.MainScreenEvent.OnNavigateToParentNode
import pro.denet.ethertreeapp.feature.navigateOnTree.presentation.MainNavigationOnTreeViewModel.MainScreenEvent.OnNodeTrashClick
import pro.denet.ethertreeapp.feature.navigateOnTree.presentation.MainNavigationOnTreeViewModel.MainScreenEvent.OnParentNodeTrashClick

class MainNavigationOnTreeViewModel(
    private val addNodeToParentUseCase: AddNodeToParentUseCase,
    private val deleteNodeUseCase: DeleteNodeUseCase,
    private val getNodeWithChildrenUseCase: GetNodeWithChildrenUseCase,
    private val getRootNodeUseCase: GetRootNodeUseCase,
) : ViewModel() {

    private val _screenState = MutableStateFlow(MainScreenState())
    val screenState: StateFlow<MainScreenState> = _screenState.asStateFlow()

    private val _screenAction = MutableSharedFlow<MainScreenAction?>()
    private val screenAction: SharedFlow<MainScreenAction?> = _screenAction.asSharedFlow()

    @Immutable
    data class MainScreenState(
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val selectedNode: NodeUiModel? = null,
        val currentNode: NodeUiModel? = null,
        val childrenCurrentNode: PersistentList<NodeUiModel> = persistentListOf()
    )

    @Immutable
    sealed interface MainScreenEvent {
        data object OnInit : MainScreenEvent
        data class OnNodeTrashClick(val idNode: Int) : MainScreenEvent
        data class OnParentNodeTrashClick(val idNode: Int) : MainScreenEvent
        data class OnAddNode(val idParent: Int) : MainScreenEvent
        data class OnNavigateToParentNode(val idNode: Int) : MainScreenEvent
        data class OnNavigateToChildNode(val idNode: Int) : MainScreenEvent
    }

    @Immutable
    sealed interface MainScreenAction {
        data class ShowSnackbar(val messageType: SnackbarMessageType, val param: String) :
            MainScreenAction

        enum class SnackbarMessageType {

        }
    }

    init {
        eventHandler(event = OnInit)
    }

    private fun eventHandler(event: MainScreenEvent) {
        when (event) {
            is OnAddNode -> onInit()
            OnInit -> TODO()
            is OnNavigateToChildNode -> TODO()
            is OnNavigateToParentNode -> TODO()
            is OnNodeTrashClick -> TODO()
            is OnParentNodeTrashClick -> TODO()
        }
    }

    private fun onInit() = viewModelScope.launch {
        _screenState.emit(
            _screenState.value.copy(
                isLoading = true,
            )
        )
        getRootNodeUseCase().fold(
            onSuccess = { nodeDto ->

                val currentNode = nodeDto.toNodeUiModel().getOrNull()

                _screenState.emit(
                    _screenState.value.copy(
                        isError = currentNode == null,
                        currentNode = currentNode
                    )
                )

                nodeDto.children.collect { listDto ->
                    _screenState.emit(
                        _screenState.value.copy(
                            childrenCurrentNode = listDto.toNodeUiModels().toPersistentList()
                        )
                    )
                }
            },
            onFailure = {
                _screenState.emit(
                    _screenState.value.copy(
                        isError = true,
                    )
                )
            }
        )
        _screenState.emit(
            _screenState.value.copy(
                isLoading = false,
            )
        )
    }
}
