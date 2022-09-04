package com.example.nasaapod.domain.scenarios

import com.example.nasaapod.domain.model.Photo
import com.example.nasaapod.domain.repository.FavoritesRepository

class DeletePhoto(
    private val repository: FavoritesRepository
) {
    suspend operator fun invoke(photo: Photo) = repository.deletePhoto(photo)
}