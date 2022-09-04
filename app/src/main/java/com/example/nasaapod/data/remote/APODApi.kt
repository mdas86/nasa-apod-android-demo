package com.example.nasaapod.data.remote

import com.example.nasaapod.utils.Constants.API_KEY
import com.example.nasaapod.domain.model.PhotoDetails
import retrofit2.http.GET
import retrofit2.http.Query

interface APODApi {
    @GET("planetary/apod?api_key=${API_KEY}")
    suspend fun getTodayPhoto(): PhotoDetails

    @GET("planetary/apod")
    suspend fun getPhotoBasedOnDate(
        @Query("date") date: String,
        @Query("api_key") apiKey: String = API_KEY
    ): PhotoDetails
}