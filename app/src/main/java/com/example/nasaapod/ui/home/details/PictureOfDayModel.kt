package com.example.nasaapod.ui.home.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasaapod.domain.model.Photo
import com.example.nasaapod.domain.repository.HomeRepository
import com.example.nasaapod.domain.scenarios.InsertPhoto
import com.example.nasaapod.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PictureOfDayModel @Inject constructor(
    private val repository: HomeRepository,
    private val insertPhoto: InsertPhoto
) : ViewModel() {

    private val _todayPhoto = MutableLiveData<Photo>()
    val todayPhoto: LiveData<Photo>
        get() = _todayPhoto

    private val _photoBasedOnDate = MutableLiveData<Photo>()
    val photoBasedOnDate: LiveData<Photo>
        get() = _photoBasedOnDate

    private val _todayPhotoChannel = Channel<UiTodayPhotoEvent>()
    val todayPhotoEvent = _todayPhotoChannel.receiveAsFlow()

    init {
        getTodayPhoto()
    }

    private fun getTodayPhoto() {
        viewModelScope.launch {
            repository.getTodayPhoto().onEach { event ->
                when (event) {
                    is Resource.Loading -> {
                        _todayPhotoChannel.send(UiTodayPhotoEvent.ShowProgressBar(true))
                    }
                    is Resource.Success -> {
                        event.data?.let { photo ->
                            _todayPhoto.postValue(photo)
                            _todayPhotoChannel.send(UiTodayPhotoEvent.ShowProgressBar(false))
                        }
                    }
                    is Resource.Error -> {
                        _todayPhotoChannel.send(UiTodayPhotoEvent.ShowSnackbar("${event.message}"))
                        _todayPhotoChannel.send(UiTodayPhotoEvent.ShowProgressBar(false))
                    }
                }
            }.launchIn(this)
        }
    }

    internal fun getPhotoBasedOnDate(date: String) {
        viewModelScope.launch {
            repository.getPhotoBasedOnDate(date).onEach { event ->
                when (event) {
                    is Resource.Loading -> {
                        _todayPhotoChannel.send(UiTodayPhotoEvent.ShowProgressBar(true))
                    }
                    is Resource.Success -> {
                        event.data?.let { photo ->
                            _photoBasedOnDate.postValue(photo)
                            _todayPhotoChannel.send(UiTodayPhotoEvent.ShowProgressBar(false))
                        }
                    }
                    is Resource.Error -> {
                        _todayPhotoChannel.send(UiTodayPhotoEvent.ShowSnackbar("${event.message}"))
                        _todayPhotoChannel.send(UiTodayPhotoEvent.ShowProgressBar(false))
                    }
                }
            }.launchIn(this)
        }
    }

    fun onEvent(event: PictureOfDayEvent) {
        when (event) {
            is PictureOfDayEvent.AddFavoritesClicked -> {
                viewModelScope.launch {
                    insertPhoto(event.photo)
                    _todayPhotoChannel.send(UiTodayPhotoEvent.ShowSnackbar("Successfully added"))
                }
            }
        }
    }


    sealed class UiTodayPhotoEvent {
        data class ShowProgressBar(val isLoading: Boolean) : UiTodayPhotoEvent()
        data class ShowSnackbar(val message: String) : UiTodayPhotoEvent()
    }
}