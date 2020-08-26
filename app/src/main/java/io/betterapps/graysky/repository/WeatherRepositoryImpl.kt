package io.betterapps.graysky.repository

import io.betterapps.graysky.data.api.ApiHelper
import io.betterapps.graysky.data.models.GeoLocation
import io.betterapps.graysky.data.models.WeatherByLocationResponse

class WeatherRepositoryImpl(val apiHelper: ApiHelper, val cache: MutableMap<Any, Any>) :
    WeatherRepository {

    override suspend fun getWeatherByLocation(geoLocation: GeoLocation): WeatherByLocationResponse {
        if (cache.containsKey(geoLocation)) {
            return cache.get(geoLocation)!! as WeatherByLocationResponse
        } else {
            val response = apiHelper.getWeather(geoLocation)
            cache[geoLocation] = response
            return response
        }
    }
}
