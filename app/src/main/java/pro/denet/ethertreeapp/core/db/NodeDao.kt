package pro.denet.ethertreeapp.core.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import pro.denet.ethertreeapp.core.sl.locateLazy
import pro.denet.ethertreeapp.core.util.CalculateAddressNode

@Dao
abstract class NodeDao {
    //INSERT INTO node (address, parent_id) VALUES ('cd2a3d9f938e13cd947ec05abc7fe734df8dd826', 1)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun saveNode(node: NodeEntity): Long

    @Query("DELETE FROM node WHERE id = :idNode")
    abstract suspend fun deleteNode(idNode: Int): Int

    @Query("SELECT * FROM node WHERE id = :idNode")
    abstract suspend fun getNodeById(idNode: Int): NodeEntity?

    @Query("SELECT * FROM node WHERE parent_id = :parentId")
    abstract fun getChildrenByParentId(parentId: Int): Flow<List<NodeEntity>>

    @Query("UPDATE node SET address = :address WHERE id = :idNode")
    abstract suspend fun addAddressNodeById(idNode: Int, address: String)

    @Query("UPDATE node SET address = :address WHERE rowid = :rowId")
    abstract suspend fun addAddressNodeByRowId(rowId: Long, address: String)

    @Query("SELECT * FROM node WHERE rowid = :rowId")
    abstract suspend fun getNodeByRowId(rowId: Long): NodeEntity?


    val calculateAddressNode: CalculateAddressNode by locateLazy<CalculateAddressNode>()

    @Transaction
    suspend fun addNodeByParentId(idParent: Int): Result<Unit> {
        val rowIdNewNode = saveNode(
            NodeEntity(
                address = null,
                parentId = idParent
            )
        )

        val nodeInDb =
            getNodeByRowId(rowIdNewNode)
                ?: return Result.failure(RuntimeException())

        val addressNewNode = calculateAddressNode(nodeInDb.id, nodeInDb.parentId ?: 1)

        val resultUpdate = addAddressNodeById(nodeInDb.id, addressNewNode)

        return Result.success(resultUpdate)
    }
}
