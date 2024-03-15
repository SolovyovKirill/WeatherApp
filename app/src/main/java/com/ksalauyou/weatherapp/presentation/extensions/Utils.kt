package com.ksalauyou.weatherapp.presentation.extensions

import kotlin.math.roundToInt

fun Float.tempToFormattedString(): String {
    return "${roundToInt()}°C"
}