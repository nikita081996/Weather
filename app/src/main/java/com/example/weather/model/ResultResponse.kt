package com.example.weather.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResultResponse(
    @Json(name = "coord")
    val coord: Coordinate? = null,
    @Json(name = "weather")
    val weather: List<Weather?>? = null,
    @Json(name = "base")
    val base: String? = null,
    @Json(name = "main")
    val main: Main? = null,

    @Json(name = "visibility")
    val visibility: String? = null,
    @Json(name = "wind")
    val wind: Main? = null,

    @Json(name = "clouds")
    val clouds: Clouds? = null,
    @Json(name = "dt")
    val dt: String? = null,

    @Json(name = "sys")
    val sys: Sys? = null,
    @Json(name = "timezone")
    val timezone: String? = null,
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "cod")
    val cod: Int? = null
)