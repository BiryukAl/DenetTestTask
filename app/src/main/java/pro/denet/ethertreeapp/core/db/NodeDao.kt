package pro.denet.ethertreeapp.core.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NodeDao {
    //INSERT INTO node (address, parent_id) VALUES ('cd2a3d9f938e13cd947ec05abc7fe734df8dd826', 1)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNode(node: NodeEntity): Long

    @Query("DELETE FROM node WHERE id = :idNode")
    suspend fun deleteNode(idNode: Int): Int

    @Query("SELECT * FROM node WHERE id = :idNode")
    suspend fun getNodeById(idNode: Int): NodeEntity?

    @Query("SELECT * FROM node WHERE parent_id = :parentId")
    fun getChildrenByParentId(parentId: Int): Flow<List<NodeEntity>>

    @Query("UPDATE node SET address = :address WHERE id = :idNode")
    suspend fun addAddressNodeById(idNode: Int, address: String)

    @Query("UPDATE node SET address = :address WHERE rowid = :rowId")
    suspend fun addAddressNodeByRowId(rowId: Long, address: String)

    @Query("SELECT * FROM node WHERE rowid = :rowId")
    suspend fun getNodeByRowId(rowId: Long): NodeEntity?
}
