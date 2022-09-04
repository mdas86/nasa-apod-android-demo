package com.example.nasaapod.di

import com.example.nasaapod.domain.repository.FavoritesRepository
import com.example.nasaapod.domain.scenarios.DeletePhoto
import com.example.nasaapod.domain.scenarios.GetPhotos
import com.example.nasaapod.domain.scenarios.InsertPhoto
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ScenarioModule {

    @Provides
    fun provideGetPhotosUseCase(repository: FavoritesRepository): GetPhotos {
        return GetPhotos(repository)
    }

    @Provides
    fun provideInsertPhotoUseCase(repository: FavoritesRepository): InsertPhoto {
        return InsertPhoto(repository)
    }

    @Provides
    fun provideDeletePhotoUseCase(repository: FavoritesRepository): DeletePhoto {
        return DeletePhoto(repository)
    }
}