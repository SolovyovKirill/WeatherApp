package com.ksalauyou.weatherapp.domain.repository

import com.ksalauyou.weatherapp.domain.entity.City

interface SearchRepository {
    suspend fun search(query: String): List<City>
}