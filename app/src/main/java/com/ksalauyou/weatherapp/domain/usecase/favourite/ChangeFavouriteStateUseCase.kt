package com.ksalauyou.weatherapp.domain.usecase.favourite

import com.ksalauyou.weatherapp.domain.entity.City
import com.ksalauyou.weatherapp.domain.repository.FavouriteRepository
import javax.inject.Inject

class ChangeFavouriteStateUseCase @Inject constructor(
    private val repository: FavouriteRepository
) {
    suspend fun addToFavourite(city: City) = repository.addToFavourite(city)

    suspend fun removeFromFavourite(cityId: Int) = repository.removeFromFavourite(cityId)
}