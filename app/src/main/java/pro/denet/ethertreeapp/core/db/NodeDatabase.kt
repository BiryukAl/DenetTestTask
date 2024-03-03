package pro.denet.ethertreeapp.core.db

import androidx.room.Database
import androidx.room.RoomDatabase


interface NodeDatabase {
    val nodeDao: NodeDao
}

@Database(
    entities = [
        NodeEntity::class
    ], version = VERSION_DB,
    exportSchema = false
)
abstract class NodeDatabaseImpl : RoomDatabase(), NodeDatabase

/*
 BuildConfig.VERSION_DB
//https://github.com/google/ksp/issues/350
*/
const val VERSION_DB: Int = 1
