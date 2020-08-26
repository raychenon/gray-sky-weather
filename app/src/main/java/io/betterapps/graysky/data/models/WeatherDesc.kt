package io.betterapps.graysky.data.models

import com.google.gson.annotations.SerializedName

data class WeatherDesc(
    @SerializedName("id") val id: Int,
    @SerializedName("main") val main: String,
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)
