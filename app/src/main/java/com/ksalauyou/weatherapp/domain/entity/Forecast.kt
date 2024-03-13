package com.ksalauyou.weatherapp.domain.entity

data class Forecast(
    val currentWeather: Weather,
    val upcoming: List<Weather>
)
