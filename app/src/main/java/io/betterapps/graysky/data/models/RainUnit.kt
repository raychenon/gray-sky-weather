package io.betterapps.graysky.data.models

import com.google.gson.annotations.SerializedName

data class RainUnit(
    // https://openweathermap.org/api/one-call-api
    // Rain volume for last hour, mm
    @SerializedName("1h") val precipitationVolumeForecast1h: Float,
)
