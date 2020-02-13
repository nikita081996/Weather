package com.example.weather.service

import com.example.weather.model.ResultResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val ENDPOINT = "https://api.openweathermap.org/"

        const val TODAY_WEATHER = "data/2.5/weather"
        const val FORECAST_WEATHER = "data/2.5/forecast"
        const val Q = "q"
        const val APPID = "appid"
        const val COUNT = "cnt"
    }

    @GET(TODAY_WEATHER)
    suspend fun updatePassword(
        @Query(APPID) appid: String,
        @Query(Q) q: String
    ): Response<ResultResponse>

}

