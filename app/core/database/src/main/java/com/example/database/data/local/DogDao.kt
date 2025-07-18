package com.example.database.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DogDao {
    @Query("SELECT * FROM dogs")
    fun getAllDogs(): Flow<List<com.example.database.data.local.DogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDogs(dogs: List<com.example.database.data.local.DogEntity>)

    @Query("DELETE FROM dogs")
    suspend fun clearDogs()
}