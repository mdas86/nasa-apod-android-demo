package com.example.nasaapod.domain.repository

import com.example.nasaapod.data.remote.APODApi
import com.example.nasaapod.domain.model.Photo
import com.example.nasaapod.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HomeRepositoryImpl(
    private val api: APODApi
) : HomeRepository {

    override fun getTodayPhoto(): Flow<Resource<Photo>> = flow {
        emit(Resource.Loading())

        try {
            emit(Resource.Success(api.getTodayPhoto().toPhotoDomain()))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message))
        }
    }

    override fun getPhotoBasedOnDate(date: String): Flow<Resource<Photo>> = flow {
        emit(Resource.Loading())

        try {
            emit(Resource.Success(api.getPhotoBasedOnDate(date).toPhotoDomain()))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message))
        }
    }
}