package com.example.nasaapod.domain.scenarios

import com.example.nasaapod.domain.repository.FavoritesRepository

class GetPhotos(
    private val repository: FavoritesRepository
) {
    operator fun invoke() = repository.getPhotos()
}