package io.betterapps.graysky.data.models

import com.google.gson.annotations.SerializedName
import java.util.TimeZone

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

    fun actualLocalTime(timeOffset: Long = 0, format: String = "HH:mm"): String {
        val sdf = java.text.SimpleDateFormat(format)
        sdf.setTimeZone(TimeZone.getTimeZone("GMT")) // set time zone independent of the local computer
        val date = java.util.Date((dateTime + timeOffset) * 1000)
        return sdf.format(date)
    }
}
