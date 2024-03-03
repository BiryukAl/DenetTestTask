package pro.denet.ethertreeapp.feature.navigateOnTree.domain.useCase

import pro.denet.ethertreeapp.feature.navigateOnTree.domain.share.NodeRepository

class AddNodeToParentUseCase(
    private val repository: NodeRepository
) {
    operator fun invoke(idParent: Int): Result<Unit> {
        return repository.addNodeToParent(idParent)
    }
}
