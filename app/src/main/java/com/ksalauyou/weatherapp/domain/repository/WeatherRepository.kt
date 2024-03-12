package com.ksalauyou.weatherapp.domain.repository

import com.ksalauyou.weatherapp.domain.entity.City
import com.ksalauyou.weatherapp.domain.entity.Forecast
import com.ksalauyou.weatherapp.domain.entity.Weather

interface WeatherRepository {

    suspend fun getWeather(cityId: Int): Weather

    suspend fun getForecast(cityId: Int): Forecast
}