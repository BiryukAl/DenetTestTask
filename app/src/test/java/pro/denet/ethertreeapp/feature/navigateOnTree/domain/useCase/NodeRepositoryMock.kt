package pro.denet.ethertreeapp.feature.navigateOnTree.domain.useCase

import kotlinx.coroutines.flow.flowOf
import pro.denet.ethertreeapp.feature.navigateOnTree.domain.share.NodeBriefDto
import pro.denet.ethertreeapp.feature.navigateOnTree.domain.share.NodeDto
import pro.denet.ethertreeapp.feature.navigateOnTree.domain.share.NodeRepository

class NodeRepositoryMock : NodeRepository {

    var idCurrent: Int = 1
        get() {
            val id = field
            field++
            return id
        }

    private val db = mutableListOf<NodeDto>()

    fun getDb(): List<NodeDto> {
        return db.toList()
    }


    private var isDbFailure: Boolean = false

    override suspend fun createRoot(): Result<Unit> {
        if (isDbFailure) return Result.failure(RuntimeException())

        db.add(
            NodeDto(
                idCurrent,
                "a",
                1,
                isRoot = true,
                children = flowOf(listOf())
            )
        )

        return Result.success(Unit)
    }

    override suspend fun getNodeWithChildren(idNode: Int): Result<NodeDto> {
        if (isDbFailure) return Result.failure(RuntimeException())
        val node = db.find { it.id == idNode }

        return if (node != null) {
            Result.success(node)
        } else {
            Result.failure(RuntimeException())
        }

    }

    override suspend fun getNodeById(idNode: Int): Result<NodeBriefDto> {
        if (isDbFailure) return Result.failure(RuntimeException())

        val node = db.find { it.id == idNode }
        return if (node != null) Result.success(
            NodeBriefDto(
                id = node.id,
                address = node.address,
                parentId = node.parentId,
                isRoot = node.isRoot
            )
        ) else Result.failure(RuntimeException())
    }

    fun dbFailure(isFailure: Boolean = false) {
        isDbFailure = isFailure
    }

    override suspend fun addNodeToParent(idParent: Int): Result<Unit> {
        val lastNodeId = db.last().id
        db.add(
            NodeDto(
                idCurrent,
                "a",
                lastNodeId,
                isRoot = true,
                children = flowOf(listOf())
            )
        )
        return Result.success(Unit)
    }

    override suspend fun deleteNode(idNode: Int): Result<Unit> {
        db.removeIf {
            it.id == idNode
        }
        return Result.success(Unit)
    }

}

