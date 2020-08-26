package io.betterapps.graysky.data.models

import com.google.gson.annotations.SerializedName

data class WeatherDaily(
    @SerializedName("dt") val time: Int,
    @SerializedName("temp") val temperature: Temperature,
    @SerializedName("feels_like") val feelsLikeTemperature: Temperature,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("clouds") val clouds: Int,
    @SerializedName("weather") val weathers: List<WeatherDesc>
) {

    fun getWeatherDesc(): WeatherDesc {
        return weathers[0]
    }
}
