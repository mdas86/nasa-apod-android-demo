package com.example.nasaapod.domain.repository

import com.example.nasaapod.domain.model.Photo
import com.example.nasaapod.utils.Resource
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun getTodayPhoto(): Flow<Resource<Photo>>

    fun getPhotoBasedOnDate(date: String): Flow<Resource<Photo>>

}