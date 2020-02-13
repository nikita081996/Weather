package com.example.weather

import android.app.Application
import com.example.weather.di.AppInjector
import com.facebook.stetho.BuildConfig
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 * Base class of Android app containing components like Activities and Services
 * Instantiated before all the activities or any other application objects have been created in Android app
 */
class AppController : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (BuildConfig.DEBUG) Stetho.initializeWithDefaults(this)
        //Timber.plant(Timber.DebugTree())
        AppInjector.init(this)
    }


    companion object {
        lateinit var instance: AppController
            private set
    }


    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector
}