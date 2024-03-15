package com.ksalauyou.weatherapp.presentation.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.ksalauyou.weatherapp.presentation.details.DetailsComponent
import com.ksalauyou.weatherapp.presentation.favourite.FavouriteComponent
import com.ksalauyou.weatherapp.presentation.search.SearchComponent

interface RootComponent {

    val stack : Value<ChildStack<*, Child>>

    sealed interface Child {
        data class Search(val component: SearchComponent) : Child
        data class Favourite(val component: FavouriteComponent) : Child
        data class Details(val component: DetailsComponent) : Child
    }
}