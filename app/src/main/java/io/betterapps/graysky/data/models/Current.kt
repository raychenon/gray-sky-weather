package io.betterapps.graysky.data.models

import com.google.gson.annotations.SerializedName


data class Current(
    @SerializedName("temp") val temperature: Float,
    @SerializedName("feels_like") val feelsLikeTemperature: Float,
    @SerializedName("humidity") val humidity: Int
)