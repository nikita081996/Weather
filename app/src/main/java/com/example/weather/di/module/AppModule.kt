package com.example.weather.di.module

import com.example.weather.di.CoroutineScopeIO
import com.example.weather.di.WeatherAPI
import com.example.weather.service.ApiService
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, CoreDataModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideWeatherService(
        @WeatherAPI okhttpClient: OkHttpClient,
        converterFactory: MoshiConverterFactory
    ) = provideService(okhttpClient, converterFactory, ApiService::class.java)


//    @Singleton
//    @Provides
//    fun provideDashboardRemoteDataSource(apiService: ApiService) =
//        DashboardRemoteDataSource(apiService)

    @WeatherAPI
    @Provides
    fun providePrivateOkHttpClient(
        upstreamClient: OkHttpClient
    ): OkHttpClient {
        return upstreamClient.newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .callTimeout(60, TimeUnit.SECONDS)
//                .addInterceptor(AuthInterceptor(BuildConfig.API_DEVELOPER_TOKEN))
            .build()
    }

    @CoroutineScopeIO
    @Provides
    fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)


    private fun createRetrofit(
        okhttpClient: OkHttpClient,
        converterFactory: MoshiConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiService.ENDPOINT)
            .client(okhttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    private fun <T> provideService(
        okhttpClient: OkHttpClient,
        converterFactory: MoshiConverterFactory, clazz: Class<T>
    ): T {
        return createRetrofit(okhttpClient, converterFactory).create(clazz)
    }
}
