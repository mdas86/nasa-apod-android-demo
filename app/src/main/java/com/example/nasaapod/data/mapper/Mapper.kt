package com.example.nasaapod.data.local

import com.example.nasaapod.domain.model.Photo

fun PhotoEntity.toPhotoDomain(): Photo {
    return Photo(
        date = date,
        explanation = explanation,
        hdurl = hdurl,
        title = title,
        url = url
    )
}

fun Photo.toPhotoEntity(): PhotoEntity {
    return PhotoEntity(
        date = date,
        explanation = explanation,
        hdurl = hdurl,
        title = title,
        url = url
    )
}