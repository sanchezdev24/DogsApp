package com.example.dogs.domain.usecase

import com.example.dogs.domain.model.Dog
import com.example.dogs.domain.repository.DogRepository
import com.example.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDogsUseCase @Inject constructor(
    private val repository: DogRepository
) {
    operator fun invoke(): Flow<Resource<List<Dog>>> = repository.getDogs()

    suspend fun refreshDogs() = repository.refreshDogs()
}