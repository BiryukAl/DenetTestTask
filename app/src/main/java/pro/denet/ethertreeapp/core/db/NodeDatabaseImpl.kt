package pro.denet.ethertreeapp.core.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NodeEntity::class], version = 1, exportSchema = false)
abstract class NodeDatabaseImpl : RoomDatabase(), NodeDatabase
