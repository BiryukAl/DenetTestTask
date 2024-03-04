package pro.denet.ethertreeapp.feature.navigateOnTree.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pro.denet.ethertreeapp.core.db.NodeEntity
import pro.denet.ethertreeapp.feature.navigateOnTree.domain.share.NodeDto
import timber.log.Timber

fun NodeEntity.toNodeDto(childrenNode: Flow<List<NodeEntity>>): Result<NodeDto> =
    kotlin.runCatching {
        NodeDto(
            id = this.id,
            address = this.address ?: "",
            parentId = this.parentId,
            isRoot = this.id == 1,
            children = childrenNode.map { listEntity ->
                listEntity.mapNotNull {
                    it.toNodeDtoBrief().getOrNull()
                }
            }
        )

    }

fun NodeEntity.toNodeDtoBrief(): Result<NodeDto.NodeBrief> = kotlin.runCatching {
    NodeDto.NodeBrief(
        idChildren = this.id,
        address = this.address ?: "",
        isRoot = false
    )
}

