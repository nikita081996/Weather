package com.example.weather.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Wind(
    @Json(name = "speed")
    val speed: String? = null,
    @Json(name = "deg")
    val deg: Int? = null
)