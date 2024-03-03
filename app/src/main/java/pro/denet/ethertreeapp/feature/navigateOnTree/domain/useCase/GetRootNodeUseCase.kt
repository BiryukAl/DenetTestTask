package pro.denet.ethertreeapp.feature.navigateOnTree.domain.useCase

import pro.denet.ethertreeapp.feature.navigateOnTree.domain.share.NodeDto
import pro.denet.ethertreeapp.feature.navigateOnTree.domain.share.NodeRepository

class GetRootNodeUseCase(
    private val repository: NodeRepository
) {
    operator fun invoke(): Result<NodeDto> {
        var root = repository.getNodeWithChildren(1)
        if (root.getOrNull() == null) {
            repository.addNodeToParent(0)
            root = repository.getNodeWithChildren(1)
        }
        return root
    }
}
