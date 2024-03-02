package pro.denet.ethertreeapp.core.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "node")
data class NodeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "address")
    val address: String,
    @ColumnInfo(name = "parent_id")
    val parentId: Int
)
