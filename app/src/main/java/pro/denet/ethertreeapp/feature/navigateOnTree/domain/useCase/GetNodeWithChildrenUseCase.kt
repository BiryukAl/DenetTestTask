package pro.denet.ethertreeapp.feature.navigateOnTree.domain.useCase

import pro.denet.ethertreeapp.feature.navigateOnTree.domain.share.NodeDto
import pro.denet.ethertreeapp.feature.navigateOnTree.domain.share.NodeRepository

class GetNodeWithChildrenUseCase(
    private val repository: NodeRepository
) {
    operator fun invoke(idNode: Int): Result<NodeDto> {
        return repository.getNodeWithChildren(idNode)
    }
}
