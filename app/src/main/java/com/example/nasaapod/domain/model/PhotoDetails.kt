package com.example.nasaapod.domain.model

data class PhotoDetails(
    val copyright: String,
    val date: String,
    val explanation: String,
    val hdurl: String?,
    val mediaType: String,
    val serviceVersion: String,
    val title: String,
    val url: String?
) {
    fun toPhotoDomain(): Photo {
        return Photo(
            date = date,
            explanation = explanation,
            hdurl = hdurl,
            title = title,
            url = url
        )
    }
}
