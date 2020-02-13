package com.example.weather.service

import android.text.TextUtils
import com.squareup.moshi.Moshi
import retrofit2.Response

/**
 * Abstract Base Data source class with Error handling
 */
abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Result.Success(body)
            }
            //todo handle the block
            val moshi = Moshi.Builder().build().adapter(ErrorResponse::class.java)
                .fromJson(response.errorBody()!!.string())

            val message: String?
            message = if (!TextUtils.isEmpty(moshi?.error))
                moshi?.error
            else if (!TextUtils.isEmpty(moshi?.message))
                moshi?.message
            else " ${response.code()} ${response.message()}"

            return error(message.toString())
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Result<T> {
        return Result.Error(message)
    }

}

