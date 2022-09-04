package com.example.nasaapod.ui.home.details

import com.example.nasaapod.domain.model.Photo

sealed class PictureOfDayEvent {
    data class AddFavoritesClicked(val photo: Photo) : PictureOfDayEvent()
}