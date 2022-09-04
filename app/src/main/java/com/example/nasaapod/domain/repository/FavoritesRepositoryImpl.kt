package com.example.nasaapod.domain.repository

import com.example.nasaapod.data.local.FavoritesDatabase
import com.example.nasaapod.data.local.toPhotoDomain
import com.example.nasaapod.data.local.toPhotoEntity
import com.example.nasaapod.domain.model.Photo
import kotlinx.coroutines.flow.map

class FavoritesRepositoryImpl(
    private val db: FavoritesDatabase
) : FavoritesRepository {

    private val dao = db.dao

    override fun getPhotos() = dao.getPhotos().map { it.map { photos -> photos.toPhotoDomain() } }

    override suspend fun insertPhoto(photo: Photo) = dao.insertPhoto(photo.toPhotoEntity())

    override suspend fun deletePhoto(photo: Photo) = dao.deletePhoto(photo.toPhotoEntity())
}