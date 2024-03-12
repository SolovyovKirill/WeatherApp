package com.ksalauyou.weatherapp.domain.entity

data class Forecast(
    val weather: Weather,
    val upcoming: List<Weather>
)
