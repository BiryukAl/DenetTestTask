package pro.denet.ethertreeapp.feature.navigateOnTree.domain.share

interface NodeRepository {
    suspend fun createRoot(): Result<Unit>
    suspend fun addNodeToParent(idParent: Int): Result<Unit>
    suspend fun deleteNode(idNode: Int): Result<Unit>
    suspend fun getNodeWithChildren(idNode: Int): Result<NodeDto>
    suspend fun getNodeById(idNode: Int): Result<NodeBriefDto>

}