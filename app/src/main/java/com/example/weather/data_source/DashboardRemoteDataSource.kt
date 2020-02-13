package com.example.weather.data_source

import com.example.weather.service.ApiService
import com.example.weather.service.BaseDataSource
import javax.inject.Inject

class DashboardRemoteDataSource @Inject constructor(private val service: ApiService) :
    BaseDataSource() {
    companion object {
        const val APPID = "a890aadd191fe1983c3347a9c4caea3e"
    }

    suspend fun updatePassword(q: String) = getResult {
        service.updatePassword(
            APPID,
            q
        )
    }


}
