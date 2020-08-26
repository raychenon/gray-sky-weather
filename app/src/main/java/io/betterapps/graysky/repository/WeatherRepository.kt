package io.betterapps.graysky.repository

import io.betterapps.graysky.data.models.GeoLocation
import io.betterapps.graysky.data.models.WeatherByLocationResponse

interface WeatherRepository {
    suspend fun getWeatherByLocation(geoLocation: GeoLocation): WeatherByLocationResponse
}
