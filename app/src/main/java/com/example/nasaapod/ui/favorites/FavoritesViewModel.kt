package com.example.nasaapod.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasaapod.domain.model.Photo
import com.example.nasaapod.domain.scenarios.DeletePhoto
import com.example.nasaapod.domain.scenarios.GetPhotos
import com.example.nasaapod.domain.scenarios.InsertPhoto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val deletePhoto: DeletePhoto,
    private val getPhotos: GetPhotos,
    private val insertPhoto: InsertPhoto
) : ViewModel() {

    val photos = getPhotos().stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )

    private val _favoritesChannel = Channel<UiFavoritesEvent>()
    val favoritesEvent = _favoritesChannel.receiveAsFlow()

    fun onEvent(event: FavoritesEvent) {
        when (event) {
            is FavoritesEvent.DeleteButtonClicked -> {
                viewModelScope.launch {
                    deletePhoto(event.photo)
                    _favoritesChannel.send(UiFavoritesEvent.ShowUndoDeletePhotoMessage(event.photo))
                }
            }
            is FavoritesEvent.DeletedPhotoRestored -> {
                viewModelScope.launch { insertPhoto(event.photo) }
            }
        }
    }

    sealed class UiFavoritesEvent {
        data class ShowUndoDeletePhotoMessage(val photo: Photo): UiFavoritesEvent()
    }
}