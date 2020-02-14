package com.example.weather.repository

import com.example.weather.data_source.DashboardRemoteDataSource
import com.example.weather.service.resultLiveData
import javax.inject.Inject

class DashboardRepository @Inject constructor(private val remoteSource: DashboardRemoteDataSource) {

    fun todayWeather(q: String) = resultLiveData(
        networkCall = { remoteSource.todayWeather(q)})

    fun forecastWeather(q: String) = resultLiveData(
        networkCall = { remoteSource.forecastWeather(q)})

}