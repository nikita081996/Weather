package com.example.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.weather.model.ResultResponse
import com.example.weather.repository.DashboardRepository
import com.example.weather.service.Result

import javax.inject.Inject

class MyViewModel @Inject constructor(repository: DashboardRepository) : ViewModel() {

    var updatePassword: (q: String) -> LiveData<Result<ResultResponse>> =
        { q -> repository.updatePassword(q) }
}