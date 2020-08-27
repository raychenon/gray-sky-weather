package io.betterapps.graysky.data.models

import com.google.gson.annotations.SerializedName

data class WeatherDaily(
    @SerializedName("temp") val temperature: Temperature,
    @SerializedName("feels_like") val feelsLikeTemperature: Temperature,
)
