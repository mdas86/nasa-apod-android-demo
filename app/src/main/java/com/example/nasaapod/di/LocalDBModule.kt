package com.example.nasaapod.di

import android.app.Application
import androidx.room.Room
import com.example.nasaapod.data.local.FavoritesDatabase
import com.example.nasaapod.domain.repository.FavoritesRepository
import com.example.nasaapod.domain.repository.FavoritesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDBModule {

    @Singleton
    @Provides
    fun provideFavoritesDatabase(application: Application): FavoritesDatabase {
        return Room.databaseBuilder(
            application,
            FavoritesDatabase::class.java,
            "favorite_photos_db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideFavoritesRepository(database: FavoritesDatabase): FavoritesRepository {
        return FavoritesRepositoryImpl(database)
    }
}