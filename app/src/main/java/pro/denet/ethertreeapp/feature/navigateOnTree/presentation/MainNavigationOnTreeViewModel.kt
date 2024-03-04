package pro.denet.ethertreeapp.feature.navigateOnTree.presentation

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import pro.denet.ethertreeapp.core.widget.model.NodeUiModel
import pro.denet.ethertreeapp.feature.navigateOnTree.domain.useCase.AddNodeToParentUseCase
import pro.denet.ethertreeapp.feature.navigateOnTree.domain.useCase.DeleteNodeUseCase
import pro.denet.ethertreeapp.feature.navigateOnTree.domain.useCase.GetNodeByIdUseCase
import pro.denet.ethertreeapp.feature.navigateOnTree.domain.useCase.GetNodeWithChildrenUseCase
import pro.denet.ethertreeapp.feature.navigateOnTree.domain.useCase.GetRootNodeUseCase
import pro.denet.ethertreeapp.feature.navigateOnTree.presentation.MainNavigationOnTreeViewModel.MainScreenEvent.OnAddNode
import pro.denet.ethertreeapp.feature.navigateOnTree.presentation.MainNavigationOnTreeViewModel.MainScreenEvent.OnInit
import pro.denet.ethertreeapp.feature.navigateOnTree.presentation.MainNavigationOnTreeViewModel.MainScreenEvent.OnNavigateToChildNode
import pro.denet.ethertreeapp.feature.navigateOnTree.presentation.MainNavigationOnTreeViewModel.MainScreenEvent.OnNavigateToParentNode
import pro.denet.ethertreeapp.feature.navigateOnTree.presentation.MainNavigationOnTreeViewModel.MainScreenEvent.OnNodeTrashClick
import pro.denet.ethertreeapp.feature.navigateOnTree.presentation.MainNavigationOnTreeViewModel.MainScreenEvent.OnParentNodeTrashClick
import timber.log.Timber

class MainNavigationOnTreeViewModel(
    private val addNodeToParentUseCase: AddNodeToParentUseCase,
    private val deleteNodeUseCase: DeleteNodeUseCase,
    private val getNodeWithChildrenUseCase: GetNodeWithChildrenUseCase,
    private val getRootNodeUseCase: GetRootNodeUseCase,
    private val getNodeByIdUseCase: GetNodeByIdUseCase,
) : ViewModel() {

    private val _screenState = MutableStateFlow(MainScreenState())
    val screenState: StateFlow<MainScreenState> = _screenState.asStateFlow()

    private val _screenAction = MutableSharedFlow<MainScreenAction?>()
    val screenAction: SharedFlow<MainScreenAction?> = _screenAction.asSharedFlow()

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
        data object OnParentNodeTrashClick : MainScreenEvent
        data class OnAddNode(val idParent: Int) : MainScreenEvent
        data object OnNavigateToParentNode : MainScreenEvent
        data class OnNavigateToChildNode(val idNode: Int) : MainScreenEvent
        data object Reload : MainScreenEvent
    }

    @Immutable
    sealed interface MainScreenAction {
        data class ShowSnackbar(val messageType: SnackbarMessageType, val param: String) :
            MainScreenAction

        enum class SnackbarMessageType {
            DELETE_SUCCESS,
            DELETE_FAILURE,
            ADD_SUCCESS,
            ADD_FAILURE,
        }
    }

    init {
        eventHandler(event = OnInit)
    }

    fun eventHandler(event: MainScreenEvent) {
        when (event) {
            is OnAddNode -> {
                Timber.d("SateScreen: before OnAddNode -> ${_screenState.value}")
                onAddNode(idParent = event.idParent)
                Timber.d("SateScreen: after OnAddNode -> ${_screenState.value}")
            }

            OnInit -> onInit()
            is OnNavigateToChildNode -> {
                onNavigateToChildNode(idNode = event.idNode)
                Timber.d("SateScreen: after OnNavigateToChildNode -> ${_screenState.value}")
            }

            is OnNavigateToParentNode -> {
                onNavigateToParentNode()
                Timber.d("SateScreen: after OnNavigateToParentNode -> ${_screenState.value}")
            }

            is OnNodeTrashClick -> {
                onNodeTrashClick(idNode = event.idNode)
                Timber.d("SateScreen: after OnNodeTrashClick -> ${_screenState.value}")
            }

            is OnParentNodeTrashClick -> {
                onParentNodeTrashClick()
                Timber.d("SateScreen: after OnParentNodeTrashClick -> ${_screenState.value}")
            }

            MainScreenEvent.Reload -> onInit()
        }
    }

    private fun onNavigateToChildNode(idNode: Int) {
        changeCurrentRootWithChildren(idNode)
    }

    private fun onNavigateToParentNode() {
        val currentNodeId = _screenState.value.currentNode?.id ?: return

        viewModelScope.launch(Dispatchers.Main) {

            getNodeByIdUseCase(currentNodeId).fold(
                onSuccess = {
                    changeCurrentRootWithChildren(it.parentId)
                },
                onFailure = {
                    _screenState.emit(
                        _screenState.value.copy(
                            isError = true,
                        )
                    )
                }
            )
        }
    }

    private fun onParentNodeTrashClick() {
        val currentNodeId = _screenState.value.currentNode?.id ?: return

        onNavigateToParentNode()

        viewModelScope.launch(Dispatchers.Main) {

            val currentNode = getNodeByIdUseCase(currentNodeId)

            currentNode.fold(
                onSuccess = {
                    deleteNodeUseCase(currentNodeId).fold(
                        onSuccess = {
                            _screenAction.emit(
                                MainScreenAction.ShowSnackbar(
                                    MainScreenAction.SnackbarMessageType.DELETE_SUCCESS,
                                    currentNodeId.toString()
                                )
                            )
                        },
                        onFailure = {
                            _screenAction.emit(
                                MainScreenAction.ShowSnackbar(
                                    MainScreenAction.SnackbarMessageType.DELETE_FAILURE,
                                    currentNodeId.toString()
                                )
                            )
                        }
                    )
                },
                onFailure = {
                    _screenAction.emit(
                        MainScreenAction.ShowSnackbar(
                            MainScreenAction.SnackbarMessageType.DELETE_FAILURE,
                            currentNodeId.toString()
                        )
                    )
                }
            )
        }
    }

    private fun onAddNode(idParent: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            addNodeToParentUseCase(idParent).fold(
                onSuccess = {
                    _screenAction.emit(
                        MainScreenAction.ShowSnackbar(
                            MainScreenAction.SnackbarMessageType.ADD_SUCCESS,
                            ""
                        )
                    )
                },
                onFailure = {
                    _screenAction.emit(
                        MainScreenAction.ShowSnackbar(
                            MainScreenAction.SnackbarMessageType.ADD_FAILURE,
                            ""
                        )
                    )
                }
            )
        }

    private fun onNodeTrashClick(idNode: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            deleteNodeUseCase(idNode).fold(
                onSuccess = {
                    _screenAction.emit(
                        MainScreenAction.ShowSnackbar(
                            MainScreenAction.SnackbarMessageType.DELETE_SUCCESS,
                            idNode.toString()
                        )
                    )
                },
                onFailure = {
                    _screenAction.emit(
                        MainScreenAction.ShowSnackbar(
                            MainScreenAction.SnackbarMessageType.DELETE_FAILURE,
                            idNode.toString()
                        )
                    )
                }
            )
        }


    private fun changeCurrentRootWithChildren(idNode: Int) {
        val isRoot: Boolean = idNode == 1
        viewModelScope.launch {
            val parent = if (isRoot) getRootNodeUseCase() else getNodeWithChildrenUseCase(idNode)

            parent.fold(
                onSuccess = { nodeDto ->

                    val currentNode = nodeDto.toNodeUiModel().getOrNull()

                    nodeDto.children
                        .flowOn(Dispatchers.Main)
                        .onStart {
                            _screenState.emit(
                                _screenState.value.copy(
                                    isLoading = true,
                                )
                            )
                        }
                        .onCompletion {
                            _screenState.emit(
                                _screenState.value.copy(
                                    isLoading = false,
                                )
                            )
                        }.catch {
                            Timber.d("Error in onInit -> getRootNodeUseCase -> flow ", it)
                            _screenState.emit(
                                _screenState.value.copy(
                                    isError = true,
                                )
                            )
                        }.collect { listDto ->
                            _screenState.emit(
                                _screenState.value.copy(
                                    isLoading = false,
                                    isError = currentNode == null,
                                    currentNode = currentNode,
                                    childrenCurrentNode = listDto
                                        .toNodeUiModels()
                                        .toPersistentList()
                                )
                            )
                        }
                    Timber.d("Success in onInit -> getRootNodeUseCase -> state: ${_screenState.value}")
                },
                onFailure = {
                    Timber.d("Error in onInit -> getRootNodeUseCase -> ", it)
                    _screenState.emit(
                        _screenState.value.copy(
                            isError = true,
                        )
                    )
                }
            )
        }
    }

    private fun onInit() {
        changeCurrentRootWithChildren(idNode = 1)
    }

}
