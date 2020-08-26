package io.betterapps.graysky.data.models

import com.google.gson.annotations.SerializedName

data class WeatherByLocationResponse(
    @SerializedName("current") val current: Current
)
