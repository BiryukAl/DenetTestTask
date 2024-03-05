package pro.denet.ethertreeapp.feature.navigateOnTree.data

import pro.denet.ethertreeapp.core.db.NodeDao
import pro.denet.ethertreeapp.core.db.NodeEntity
import pro.denet.ethertreeapp.core.util.CalculateAddressNode
import pro.denet.ethertreeapp.feature.navigateOnTree.domain.share.NodeBriefDto
import pro.denet.ethertreeapp.feature.navigateOnTree.domain.share.NodeDto
import pro.denet.ethertreeapp.feature.navigateOnTree.domain.share.NodeRepository

class NodeRepositoryImpl(
    private val nodeDao: NodeDao,
    private val calculateAddressNode: CalculateAddressNode,
) : NodeRepository {


    override suspend fun createRoot(): Result<Unit> {
        try {
            val root = nodeDao.getNodeById(1)
            if (root == null) {
                val rowId = nodeDao.saveNode(
                    NodeEntity(
                        address = null,
                        parentId = null,
                    )
                )

                val nodeInDb =
                    nodeDao.getNodeByRowId(rowId)
                        ?: return Result.failure(RuntimeException())

                val addressNewNode = calculateAddressNode(nodeInDb.id, nodeInDb.parentId ?: 1)

                val resultUpdate = nodeDao.addAddressNodeById(nodeInDb.id, addressNewNode)
                return Result.success(resultUpdate)
            }
        } catch (ex: Exception) {
            return Result.failure(ex)
        }
        return Result.success(Unit)
    }


    override suspend fun addNodeToParent(idParent: Int): Result<Unit> {
        return try {
            nodeDao.addNodeByParentId(idParent)
        } catch (ex: Exception) {
            Result.failure(ex)
        }
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

    override suspend fun getNodeById(idNode: Int): Result<NodeBriefDto> {
        val node = nodeDao.getNodeById(idNode)
        return node?.toNodeBriefDto() ?: Result.failure(RuntimeException())
    }
}
