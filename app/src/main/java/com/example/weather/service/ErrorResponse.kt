package com.example.weather.service


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorResponse(
    @Json(name = "error")
    val error: String? = null,
    @Json(name = "Message")
    val message: String? = null,
    @Json(name = "Result")
    val result: Boolean? = null,
    @Json(name = "StatusCode")
    val statusCode: Int? = null
)