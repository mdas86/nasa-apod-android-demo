package com.example.nasaapod.domain.scenarios

import com.example.nasaapod.domain.model.Photo
import com.example.nasaapod.domain.repository.FavoritesRepository

class InsertPhoto(
    private val repository: FavoritesRepository
) {
    suspend operator fun  invoke(photo: Photo) = repository.insertPhoto(photo)
}