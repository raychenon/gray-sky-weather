package io.betterapps.graysky.utils

import java.text.SimpleDateFormat
import java.util.TimeZone

object TimeUtils {

    val dateFormat = instantiateDateFormat()

    // https://stackoverflow.com/questions/51811391/utils-class-in-kotlin
    @JvmStatic
    fun formatLocalTime(dateTime: Long, timeOffset: Long = 0): String {
        val date = java.util.Date((dateTime + timeOffset) * 1000)
        return dateFormat.format(date)
    }

    @JvmStatic
    fun hourLocalTime(dateTime: Long, timeOffset: Long = 0): Long {
        val date = java.util.Date((dateTime + timeOffset) * 1000)
        val hour = date.time % 86400000 / 3600000
        return hour;
    }

    @JvmStatic
    fun formatLocalTime(dateTime: Long, format: String, timeOffset: Long = 0): String {
        val dateFormat = instantiateDateFormat(format)
        val date = java.util.Date((dateTime + timeOffset) * 1000)
        return dateFormat.format(date)
    }

    private fun instantiateDateFormat(format: String = "HH:mm"): SimpleDateFormat {
        val sdf = SimpleDateFormat(format)
        sdf.setTimeZone(TimeZone.getTimeZone("GMT")) // IMPORTANT: set time zone independent of the local machine
        return sdf
    }
}
