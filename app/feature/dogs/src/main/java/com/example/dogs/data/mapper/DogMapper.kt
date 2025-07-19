package com.example.dogs.data.mapper

import com.example.database.data.local.DogEntity
import com.example.network.data.remote.DogDto
import com.example.dogs.domain.model.Dog

object DogMapper {
    fun dtoToDomain(dto: DogDto): Dog {
        return Dog(
            dogName = dto.dogName,
            description = dto.description,
            age = dto.age,
            url = dto.url
        )
    }

    fun domainToEntity(dog: Dog): DogEntity {
        return DogEntity(
            dogName = dog.dogName,
            description = dog.description,
            age = dog.age,
            url = dog.url
        )
    }

    fun entityToDomain(entity: DogEntity): Dog {
        return Dog(
            dogName = entity.dogName,
            description = entity.description,
            age = entity.age,
            url = entity.url
        )
    }
}