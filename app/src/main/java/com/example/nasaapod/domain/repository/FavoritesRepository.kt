package com.example.nasaapod.domain.repository

import com.example.nasaapod.domain.model.Photo
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {

    fun getPhotos(): Flow<List<Photo>>

    suspend fun insertPhoto(photo: Photo)

    suspend fun deletePhoto(photo: Photo)
}