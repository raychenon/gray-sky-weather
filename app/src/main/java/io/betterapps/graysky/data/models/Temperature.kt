package io.betterapps.graysky.data.models

import com.google.gson.annotations.SerializedName

data class Temperature(
    @SerializedName("day") val day: Float,
    @SerializedName("min") val min: Float?,
    @SerializedName("max") val max: Float?,
    @SerializedName("night") val night: Float,
    @SerializedName("eve") val evening: Float,
    @SerializedName("morn") val morning: Float
)
