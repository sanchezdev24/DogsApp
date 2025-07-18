package com.example.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.database.data.local.DogDao
import com.example.database.data.local.DogEntity

@Database(
    entities = [DogEntity::class],
    version = DatabaseConstants.DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dogDao(): DogDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DatabaseConstants.DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}