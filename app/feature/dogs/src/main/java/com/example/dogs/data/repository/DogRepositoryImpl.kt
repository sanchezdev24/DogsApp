package com.example.dogs.data.repository

import com.example.dogs.data.mapper.DogMapper
import com.example.dogs.domain.model.Dog
import com.example.dogs.domain.repository.DogRepository
import com.example.network.data.remote.DogApiService
import com.example.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DogRepositoryImpl @Inject constructor(
    private val apiService: DogApiService,
    private val dogDao: com.example.database.data.local.DogDao
) : DogRepository {

    override fun getDogs(): Flow<Resource<List<Dog>>> = flow {
        emit(Resource.Loading())

        try {
            // Primero intenta obtener datos locales
            dogDao.getAllDogs().map { entities ->
                entities.map { DogMapper.entityToDomain(it) }
            }.collect { localDogs ->
                if (localDogs.isNotEmpty()) {
                    emit(Resource.Success(localDogs))
                } else {
                    // Si no hay datos locales, obtener de la API
                    refreshDogs()
                    dogDao.getAllDogs().map { entities ->
                        entities.map { DogMapper.entityToDomain(it) }
                    }.collect { dogs ->
                        emit(Resource.Success(dogs))
                    }
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error desconocido"))
        }
    }

    override suspend fun refreshDogs() {
        try {
            val response = apiService.getDogs()
            if (response.isSuccessful && response.body() != null) {
                val dogs = response.body()!!.map { DogMapper.dtoToDomain(it) }
                val entities = dogs.map { DogMapper.domainToEntity(it) }

                dogDao.clearDogs()
                dogDao.insertDogs(entities)
            }
        } catch (e: Exception) {
            throw e
        }
    }
}