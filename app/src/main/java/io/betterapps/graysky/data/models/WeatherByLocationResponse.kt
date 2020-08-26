package io.betterapps.graysky.data.models

import com.google.gson.annotations.SerializedName

data class WeatherByLocationResponse(
    // timezone_offset: Shift in seconds from UTC
    @SerializedName("timezone_offset") val timezoneOffset: Long,
    @SerializedName("timezone") val timezone: String,

    @SerializedName("current") val current: WeatherUnit,
    @SerializedName("hourly") val hourly: List<WeatherUnit>,
    @SerializedName("daily") val daily: List<WeatherDaily>,
)
