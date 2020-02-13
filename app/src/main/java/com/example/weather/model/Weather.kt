package com.example.weather.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Weather(
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "description")
    val description: String? = null,

    @Json(name = "main")
    val main: String? = null,
    @Json(name = "icon")
    val icon: String? = null
)