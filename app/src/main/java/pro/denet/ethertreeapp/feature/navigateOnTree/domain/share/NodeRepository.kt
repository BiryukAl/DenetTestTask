package pro.denet.ethertreeapp.feature.navigateOnTree.domain.share

interface NodeRepository {
    fun addNodeToParent(idParent: Int): Result<Unit>
    fun deleteNode(idNode: Int): Result<Unit>
    fun getNodeWithChildren(idNode: Int): Result<NodeDto>

}