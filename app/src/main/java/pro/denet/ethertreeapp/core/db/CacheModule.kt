package pro.denet.ethertreeapp.core.db

import android.content.Context
import androidx.room.Room
import pro.denet.ethertreeapp.BuildConfig

interface CacheModule {
    fun provideDataBase(): NodeDatabase

    class Base(private val context: Context) : CacheModule {

        private val database by lazy {
            return@lazy Room.databaseBuilder(
                context,
                NodeDatabaseImpl::class.java,
                BuildConfig.DB_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }

        override fun provideDataBase(): NodeDatabase = database
    }

    class Mock(private val context: Context) : CacheModule {
        private val database by lazy {
            Room.inMemoryDatabaseBuilder(context, NodeDatabaseImpl::class.java)
                .build()
        }

        override fun provideDataBase(): NodeDatabase = database
    }

}