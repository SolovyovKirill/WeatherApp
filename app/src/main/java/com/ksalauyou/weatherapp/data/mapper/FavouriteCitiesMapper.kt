package com.ksalauyou.weatherapp.data.mapper

import com.ksalauyou.weatherapp.data.local.model.CityDbModel
import com.ksalauyou.weatherapp.domain.entity.City

fun City.toDbModel(): CityDbModel {
    return CityDbModel(
        id = id,
        name = name,
        country = country
    )
}

fun CityDbModel.toEntity(): City {
    return City(
        id = id,
        name = name,
        country = country
    )
}

fun List<CityDbModel>.toEntities(): List<City> {
    return map { it.toEntity() }
}