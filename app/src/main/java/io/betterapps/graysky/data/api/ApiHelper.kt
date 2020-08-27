package io.betterapps.graysky.data.api

import io.betterapps.graysky.const.GlobalConstants
import io.betterapps.graysky.data.domains.GeoLocation
import io.betterapps.graysky.data.network.OpenWeatherMapService

class ApiHelper(private val openWeatherMapService: OpenWeatherMapService) {

    private val options =
        mapOf<String, String>("units" to "metric", "appid" to GlobalConstants.API_ID)

    suspend fun getWeather(geoLocation: GeoLocation) =
        openWeatherMapService.getForecast(geoLocation.latitude, geoLocation.longitude, options)
}
