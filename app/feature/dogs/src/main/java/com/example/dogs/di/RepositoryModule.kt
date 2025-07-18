package com.example.dogs.di

import com.example.dogs.data.repository.DogRepositoryImpl
import com.example.dogs.domain.repository.DogRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindDogRepository(
        dogRepositoryImpl: DogRepositoryImpl
    ): DogRepository
}