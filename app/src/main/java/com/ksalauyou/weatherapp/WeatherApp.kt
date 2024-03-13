package com.ksalauyou.weatherapp

import android.app.Application
import com.ksalauyou.weatherapp.di.ApplicationComponent
import com.ksalauyou.weatherapp.di.DaggerApplicationComponent

class WeatherApp: Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }
}