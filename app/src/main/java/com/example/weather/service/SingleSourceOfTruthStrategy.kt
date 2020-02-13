package com.example.weather.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers


/**
 * The database serves as the single source of truth.
 * Therefore UI can receive data updates from database only.
 * Function notify UI about:
 * [Result.Success] - with data from database
 * [Result.Error] - if Error has occurred from any source
 * [Result.Loading]
 */
fun <A> resultLiveData(networkCall: suspend () -> Result<A>): LiveData<Result<A>> =
    liveData(Dispatchers.IO) {
        emit(Result.Loading<A>())

        val responseStatus = networkCall.invoke()
        if (responseStatus is Result.Success) {
            emit(Result.Success(responseStatus.data))
        } else if (responseStatus is Result.Error) {
            emit(Result.Error<A>(responseStatus.message))

        }
    }