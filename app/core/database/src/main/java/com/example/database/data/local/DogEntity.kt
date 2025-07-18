package com.example.database.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.database.DatabaseConstants

@Entity(tableName = DatabaseConstants.DOGS_TABLE)
data class DogEntity(
    @PrimaryKey
    val dogName: String,
    val description: String,
    val age: Int,
    val url: String
)