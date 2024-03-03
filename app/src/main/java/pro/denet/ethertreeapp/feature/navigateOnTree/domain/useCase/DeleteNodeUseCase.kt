package pro.denet.ethertreeapp.feature.navigateOnTree.domain.useCase

import pro.denet.ethertreeapp.feature.navigateOnTree.domain.share.DeleteRootException
import pro.denet.ethertreeapp.feature.navigateOnTree.domain.share.NodeRepository

class DeleteNodeUseCase(
    private val repository: NodeRepository
) {
    operator fun invoke(idNode: Int): Result<Unit> {
        if (idNode == 1) {
            return Result.failure(DeleteRootException())
        }
        return repository.deleteNode(idNode)
    }
}
