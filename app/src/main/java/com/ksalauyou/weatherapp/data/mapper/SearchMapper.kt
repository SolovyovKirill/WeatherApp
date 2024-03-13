package com.ksalauyou.weatherapp.data.mapper

import com.ksalauyou.weatherapp.data.network.dto.CityDto
import com.ksalauyou.weatherapp.domain.entity.City

fun CityDto.toEntity(): City {
    return City(
        id = id,
        name = name,
        country = country
    )
}

fun List<CityDto>.toEntities(): List<City> {
    return map { it.toEntity() }
}