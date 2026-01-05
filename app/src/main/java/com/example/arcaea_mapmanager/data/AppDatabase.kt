package com.example.arcaea_mapmanager.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SongEntity::class], version = 2, exportSchema = false) // versionを2に
abstract class AppDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "arcaea_database"
                )
                    .fallbackToDestructiveMigration() // 開発中はこれでOK
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}