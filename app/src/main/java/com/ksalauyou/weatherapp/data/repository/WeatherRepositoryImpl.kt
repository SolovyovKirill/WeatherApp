package com.ksalauyou.weatherapp.data.repository

import com.ksalauyou.weatherapp.data.mapper.toEntity
import com.ksalauyou.weatherapp.data.network.api.ApiService
import com.ksalauyou.weatherapp.domain.entity.Forecast
import com.ksalauyou.weatherapp.domain.entity.Weather
import com.ksalauyou.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : WeatherRepository {
    override suspend fun getWeather(cityId: Int): Weather =
        apiService.loadCurrentWeather("$PREFIX_CITY_ID$cityId").toEntity()

    override suspend fun getForecast(cityId: Int): Forecast =
        apiService.loadForecast("$PREFIX_CITY_ID$cityId").toEntity()

    private companion object {
        private const val PREFIX_CITY_ID = "id:"
    }
}