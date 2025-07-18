package com.example.dogs.di

import com.example.network.ApiService
import com.example.network.data.remote.DogApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = ApiService.retrofit

    @Provides
    @Singleton
    fun provideDogApiService(retrofit: Retrofit): DogApiService =
        retrofit.create(DogApiService::class.java)
}