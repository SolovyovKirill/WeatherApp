package com.ksalauyou.weatherapp.presentation.search

import com.ksalauyou.weatherapp.domain.entity.City
import com.ksalauyou.weatherapp.presentation.favourite.FavouriteStore
import kotlinx.coroutines.flow.StateFlow

interface SearchComponent {

    val model : StateFlow<SearchStore.State>

    fun onChangeSearchQuery(query: String)

    fun onClickBack()

    fun onClickSearch()

    fun onClickCity(city: City)
}