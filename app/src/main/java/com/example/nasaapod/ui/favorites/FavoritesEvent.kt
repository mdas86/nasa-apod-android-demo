package com.example.nasaapod.ui.favorites

import com.example.nasaapod.domain.model.Photo

sealed class FavoritesEvent {
    data class DeleteButtonClicked(val photo: Photo) : FavoritesEvent()
    data class DeletedPhotoRestored(val photo: Photo): FavoritesEvent()
}