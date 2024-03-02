package pro.denet.ethertreeapp.core.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNode(node: NodeEntity): Long

    @Query("DELETE FROM node WHERE id = :idNode")
    suspend fun deleteNode(idNode: Int): Int

    @Query("SELECT * FROM node WHERE id = :idNode")
    suspend fun getNodeById(idNode: Int): NodeEntity?

    @Query("SELECT * FROM node WHERE parent_id = :parentId")
    fun getChildrenByParentId(parentId: Int): Flow<List<NodeEntity>>
}
