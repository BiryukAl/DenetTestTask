package pro.denet.ethertreeapp.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import pro.denet.ethertreeapp.BuildConfig


interface NodeDatabase {
    val nodeDao: NodeDao
}

@Database(
    entities = [
        NodeEntity::class
    ], version = BuildConfig.VERSION_DB,
    exportSchema = false
)
abstract class NodeDatabaseImpl : RoomDatabase(), NodeDatabase
