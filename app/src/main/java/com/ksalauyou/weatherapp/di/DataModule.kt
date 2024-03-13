package com.ksalauyou.weatherapp.di

import android.content.Context
import com.ksalauyou.weatherapp.data.local.db.FavouriteDatabase
import com.ksalauyou.weatherapp.data.network.api.ApiFactory
import com.ksalauyou.weatherapp.data.network.api.ApiService
import com.ksalauyou.weatherapp.data.repository.FavouriteRepositoryImpl
import com.ksalauyou.weatherapp.data.repository.SearchRepositoryImpl
import com.ksalauyou.weatherapp.data.repository.WeatherRepositoryImpl
import com.ksalauyou.weatherapp.domain.repository.FavouriteRepository
import com.ksalauyou.weatherapp.domain.repository.SearchRepository
import com.ksalauyou.weatherapp.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @[ApplicationScope Binds]
    fun bindFavouriteRepository(impl: FavouriteRepositoryImpl): FavouriteRepository

    @[ApplicationScope Binds]
    fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

    @[ApplicationScope Binds]
    fun bindSearchRepository(impl: SearchRepositoryImpl): SearchRepository

    companion object {
        @[ApplicationScope Provides]
        fun provideApiService(): ApiService = ApiFactory.apiService

        @[ApplicationScope Provides]
        fun provideFavouriteDatabase(context: Context) = FavouriteDatabase.getInstance(context)

        @[ApplicationScope Provides]
        fun provideFavouriteCitiesDao(db: FavouriteDatabase) = db.favouriteCitiesDao()
    }
}