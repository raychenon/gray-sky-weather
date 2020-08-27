package io.betterapps.graysky.data.models

import com.google.gson.annotations.SerializedName
import io.betterapps.graysky.utils.TimeUtils

// todo find a better name
data class WeatherUnit(
    // dt: Data receiving time (in unix, UTC format). dt is the time of data receiving in unixtime GMT (greenwich mean time).
    @SerializedName("dt") val dateTime: Long,
    @SerializedName("temp") val temperature: Float,
    @SerializedName("feels_like") val feelsLikeTemperature: Float,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("clouds") val clouds: Int,
    @SerializedName("weather") val weathers: List<WeatherDesc>
) {

    fun getWeatherDesc(): WeatherDesc {
        return weathers[0]
    }

    /**
     * see https://openweathermap.org/weather-conditions
     */
    fun iconURL(): String {
        val iconCode = getWeatherDesc().icon
        val url = "https://openweathermap.org/img/wn/$iconCode@2x.png"
        return url
    }

    fun actualLocalTime(timeOffset: Long = 0): String {
        return TimeUtils.formatLocalTime(dateTime, timeOffset)
    }

    // may be useful to change the date format depending on the Locale
    fun actualLocalTime(timeOffset: Long = 0, format: String): String {
        return TimeUtils.formatLocalTime(dateTime, format, timeOffset)
    }
}
