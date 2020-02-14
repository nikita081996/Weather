package com.example.weather.di.module

import com.example.weather.ForecastWeatherFragment
import com.example.weather.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeForecastWeatherFragment(): ForecastWeatherFragment

}
