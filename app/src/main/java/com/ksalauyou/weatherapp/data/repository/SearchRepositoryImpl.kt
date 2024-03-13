package com.ksalauyou.weatherapp.data.repository

import com.ksalauyou.weatherapp.data.mapper.toEntities
import com.ksalauyou.weatherapp.data.network.api.ApiService
import com.ksalauyou.weatherapp.domain.entity.City
import com.ksalauyou.weatherapp.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : SearchRepository {
    override suspend fun search(query: String): List<City> {
       return apiService.searchCity(query).toEntities()
    }
}