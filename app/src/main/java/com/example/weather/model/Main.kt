package com.example.weather.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Main(
    @Json(name = "temp")
    val temp: String? = null,
    @Json(name = "feels_like")
    val feels_like: String? = null,
    @Json(name = "temp_min")
    val temp_min: String? = null,
    @Json(name = "temp_max")
    val temp_max: String? = null,
    @Json(name = "pressure")
    val pressure: String? = null,
    @Json(name = "humidity")
    val humidity: String? = null
)
