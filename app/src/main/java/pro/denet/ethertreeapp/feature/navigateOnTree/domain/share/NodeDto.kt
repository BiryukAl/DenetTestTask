package pro.denet.ethertreeapp.feature.navigateOnTree.domain.share

import kotlinx.coroutines.flow.Flow


data class NodeDto(
    val id: Int,
    val address: String,
    val parentId: Int,
    val isRoot: Boolean,
    val children: Flow<List<NodeBrief>>
) {
    data class NodeBrief(
        val idChildren: Int,
        val address: String,
        val isRoot: Boolean,
    )
}
