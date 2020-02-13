package com.example.weather.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Sys(
    @Json(name = "type")
    val type: String? = null,
    @Json(name = "id")
    val id: Int? = null,

    @Json(name = "country")
    val country: String? = null,

    @Json(name = "sunrise")
    val sunrise: String? = null,
    @Json(name = "sunset")
    val sunset: String? = null
)