package com.example.weather.utility

import java.text.DecimalFormat

object KelvinToCelsiusConverter {

    private val df2 = DecimalFormat("#.#")

    fun convertKelToCel(kel: Double): String {
        return df2.format(kel - 273.15).plus(0x00B0.toChar())
    }
}