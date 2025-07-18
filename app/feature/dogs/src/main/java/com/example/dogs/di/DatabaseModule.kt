package com.example.dogs.di

import android.content.Context
import androidx.room.Room
import com.example.database.AppDatabase
import com.example.database.DatabaseConstants
import com.example.database.data.local.DogDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DatabaseConstants.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideDogDao(database: AppDatabase): DogDao = database.dogDao()
}