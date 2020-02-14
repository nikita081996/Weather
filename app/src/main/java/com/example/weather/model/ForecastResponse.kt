package com.example.weather.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForecastResponse(
    @Json(name = "cod")
    val cod: Int? = null,
    @Json(name = "message")
    val message: String? = null,
    @Json(name = "cnt")
    val cnt: Int? = null,
    @Json(name = "list")
    val list: List<ResultResponse?>? = null,
    @Json(name = "city")
    val city: City? = null
)