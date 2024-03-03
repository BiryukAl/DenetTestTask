package pro.denet.ethertreeapp.feature.navigateOnTree.data

import pro.denet.ethertreeapp.core.db.NodeDatabase
import pro.denet.ethertreeapp.core.db.NodeEntity
import pro.denet.ethertreeapp.core.util.CalculateAddressNode
import pro.denet.ethertreeapp.feature.navigateOnTree.domain.share.NodeDto
import pro.denet.ethertreeapp.feature.navigateOnTree.domain.share.NodeRepository

class NodeRepositoryImpl(
    private val nodeDatabase: NodeDatabase,
    private val calculateAddressNode: CalculateAddressNode,
) : NodeRepository {

    private val nodeDao get() = nodeDatabase.nodeDao

    override suspend fun addNodeToParent(idParent: Int): Result<Unit> {
        val rowIdNewNode = nodeDao.saveNode(
            NodeEntity(
                address = null,
                parentId = idParent
            )
        )

        val nodeInDb =
            nodeDao.getNodeByRowId(rowIdNewNode)
                ?: return Result.failure(RuntimeException())

        val addressNewNode = calculateAddressNode(nodeInDb.id, nodeInDb.parentId)

        val resultUpdate = nodeDao.addAddressNodeById(nodeInDb.id, addressNewNode)

        return Result.success(resultUpdate)
    }

    override suspend fun deleteNode(idNode: Int): Result<Unit> {
        val result = nodeDao.deleteNode(idNode)
        return if (result > 0) {
            Result.success(Unit)
        } else Result.failure(RuntimeException())
    }

    override suspend fun getNodeWithChildren(idNode: Int): Result<NodeDto> {

        val parentNode = nodeDao.getNodeById(idNode) ?: return Result.failure(RuntimeException())

        val childrenNode = nodeDao.getChildrenByParentId(parentNode.id)

        return parentNode.toNodeDto(childrenNode)

    }
}
