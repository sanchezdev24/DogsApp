package com.example.dogs.domain.repository

import com.example.dogs.domain.model.Dog
import com.example.utils.Resource
import kotlinx.coroutines.flow.Flow

interface DogRepository {
    fun getDogs(isFirst: Boolean = false): Flow<Resource<List<Dog>>>
    suspend fun refreshDogs()
}