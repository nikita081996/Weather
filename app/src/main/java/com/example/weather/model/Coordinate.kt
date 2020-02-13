package com.example.weather.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Coordinate(
    @Json(name = "lon")
    val lon: String? = null,
    @Json(name = "lat")
    val lat: String? = null
)