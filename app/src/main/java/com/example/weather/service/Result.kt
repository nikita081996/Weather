package com.example.weather.service

sealed class Result<out T> {
    class Loading<T> : Result<T>()
    data class Error<T>(val message: String) : Result<T>()
    data class Success<T>(val data: T) : Result<T>()
}
