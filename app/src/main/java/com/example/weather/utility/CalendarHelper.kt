package com.example.weather.utility

import java.text.SimpleDateFormat
import java.util.*

object CalendarHelper {

    const val currentDateFormat = "yyyy-mm-dd HH:mm:ss"
    const val dateFormat = "dd MMM HH:mm"

    fun getCurrentDateTime(dateVal: String?): String {
        val dateFormat = SimpleDateFormat(dateFormat, Locale.US)
        val date1 = SimpleDateFormat(currentDateFormat).parse(dateVal)
        return dateFormat.format(date1)
    }


}