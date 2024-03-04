package pro.denet.ethertreeapp.feature.navigateOnTree.presentation

import pro.denet.ethertreeapp.core.widget.model.NodeUiModel
import pro.denet.ethertreeapp.feature.navigateOnTree.domain.share.NodeDto

fun NodeDto.toNodeUiModel(): Result<NodeUiModel> = kotlin.runCatching {
    NodeUiModel(
        id = this.id,
        address = this.address,
        isRoot = this.isRoot,
    )
}

private fun NodeDto.NodeBrief.toNodeUiModel(): Result<NodeUiModel> = kotlin.runCatching {
    NodeUiModel(
        id = this.idChildren,
        address = this.address,
        isRoot = this.isRoot,
    )
}

fun List<NodeDto.NodeBrief>.toNodeUiModels(): List<NodeUiModel> {
    return this.mapNotNull { it.toNodeUiModel().getOrNull() }
}