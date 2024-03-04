package pro.denet.ethertreeapp.feature.navigateOnTree.domain.useCase

import pro.denet.ethertreeapp.feature.navigateOnTree.domain.share.NodeBriefDto
import pro.denet.ethertreeapp.feature.navigateOnTree.domain.share.NodeRepository

class GetNodeByIdUseCase(
    private val repository: NodeRepository
) {
    suspend operator fun invoke(idNode: Int): Result<NodeBriefDto> {
        return repository.getNodeById(idNode)
    }
}
