package com.ksalauyou.weatherapp.presentation.favourite

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.ksalauyou.weatherapp.domain.entity.City
import com.ksalauyou.weatherapp.domain.usecase.favourite.GetFavouriteCitiesUseCase
import com.ksalauyou.weatherapp.domain.usecase.weather.GetCurrentWeatherUseCase
import com.ksalauyou.weatherapp.presentation.favourite.FavouriteStore.*
import kotlinx.coroutines.launch
import javax.inject.Inject

interface FavouriteStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object ClickSearch : Intent

        data object ClickAddToFavourite : Intent

        data class CityItemClicked(val city: City) : Intent
    }

    data class State(
        val cityItems: List<CityItem>,
    ) {

        data class CityItem(
            val city: City,
            val weatherState: WeatherState,
        )

        sealed interface WeatherState {
            data object Initial : WeatherState
            data object Error : WeatherState
            data object Loading : WeatherState

            data class Loaded(
                val temC: Float,
                val iconUrl: String,
            ) : WeatherState
        }
    }

    sealed interface Label {
        data object ClickSearch : Label

        data object ClickToFavourite : Label

        data class CityItemClicked(val city: City) : Label
    }
}

class FavouriteStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val getFavouriteCitiesUseCase: GetFavouriteCitiesUseCase,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
) {

    fun create(): FavouriteStore =
        object : FavouriteStore, Store<Intent, State, Label> by storeFactory.create(
            name = "FavouriteStore",
            initialState = State(listOf()),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data class FavouriteCitiesLoaded(val cities: List<City>) : Action
    }

    private sealed interface Msg {
        data class FavouriteCitiesLoaded(val cities: List<City>) : Msg

        data class WeatherLoaded(
            val cityId: Int,
            val temC: Float,
            val conditionIconUrl: String,
        ) : Msg

        data class WeatherLoadingError(
            val cityId: Int,
        ) : Msg

        data class WeatherIsLoading(
            val cityId: Int,
        ) : Msg

    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                getFavouriteCitiesUseCase().collect {
                    dispatch(Action.FavouriteCitiesLoaded(it))
                }

            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when(intent) {
                is Intent.CityItemClicked -> {
                    publish(Label.CityItemClicked(intent.city))
                }
                Intent.ClickAddToFavourite -> {
                    publish(Label.ClickToFavourite)
                }
                Intent.ClickSearch -> {
                    publish(Label.ClickSearch)
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {

            when (action) {
                is Action.FavouriteCitiesLoaded -> {
                    val cities = action.cities
                    dispatch(Msg.FavouriteCitiesLoaded(cities))
                    cities.forEach {
                        scope.launch {
                            loadWeatherForCity(it)
                        }
                    }
                }
            }
        }

        private suspend fun loadWeatherForCity(city: City) {
            dispatch(Msg.WeatherIsLoading(city.id))
            try {
                val weather = getCurrentWeatherUseCase(city.id)
                dispatch(
                    Msg.WeatherLoaded(
                        cityId = city.id,
                        temC = weather.tempC,
                        conditionIconUrl = weather.conditionUrl
                    )
                )
            } catch (e: Exception) {
                dispatch(Msg.WeatherLoadingError(city.id))
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State = when(msg) {
            is Msg.FavouriteCitiesLoaded -> {
                copy(
                    cityItems = msg.cities.map { city ->
                        State.CityItem(
                            city = city,
                            weatherState = State.WeatherState.Initial
                        )
                    }
                )
            }
            is Msg.WeatherIsLoading -> {
                copy(
                    cityItems = cityItems.map { cityItem ->
                        if (cityItem.city.id == msg.cityId) {
                            cityItem.copy(weatherState = State.WeatherState.Loading)
                        } else {
                            cityItem
                        }
                    }
                )
            }
            is Msg.WeatherLoaded -> {
                copy(
                    cityItems = cityItems.map { cityItem ->
                        if (cityItem.city.id == msg.cityId) {
                            cityItem.copy(weatherState = State.WeatherState.Loaded(
                                temC = msg.temC,
                                iconUrl = msg.conditionIconUrl
                            ))
                        } else {
                            cityItem
                        }
                    }
                )
            }
            is Msg.WeatherLoadingError -> {
                copy(
                    cityItems = cityItems.map { cityItem ->
                        if (cityItem.city.id == msg.cityId) {
                            cityItem.copy(weatherState = State.WeatherState.Error)
                        } else {
                            cityItem
                        }
                    }
                )
            }
        }
    }
}
