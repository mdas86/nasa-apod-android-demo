package com.example.nasaapod.domain.model

data class Photo(
    val date: String,
    val explanation: String,
    val hdurl: String?,
    val title: String,
    val url: String?
)
