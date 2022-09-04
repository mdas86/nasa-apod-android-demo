package com.example.nasaapod.di

import com.example.nasaapod.data.remote.APODApi
import com.example.nasaapod.domain.repository.HomeRepository
import com.example.nasaapod.domain.repository.HomeRepositoryImpl
import com.example.nasaapod.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHomeApi(): APODApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APODApi::class.java)
    }

    @Provides
    @Singleton
    fun provideHomeRepository(api: APODApi): HomeRepository {
        return HomeRepositoryImpl(api)
    }
}