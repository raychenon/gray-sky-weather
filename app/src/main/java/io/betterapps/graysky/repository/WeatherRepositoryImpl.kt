package io.betterapps.graysky.repository

import io.betterapps.graysky.data.api.ApiHelper
import io.betterapps.graysky.data.db.entities.LocationDao
import io.betterapps.graysky.data.domains.GeoLocation
import io.betterapps.graysky.data.models.WeatherByLocationResponse

class WeatherRepositoryImpl(private val dao: LocationDao, val apiHelper: ApiHelper, val cache: MutableMap<GeoLocation, WeatherByLocationResponse>) :
    WeatherRepository {

    // todo cache could persist not only be in-memory
    override suspend fun getWeatherByLocation(geoLocation: GeoLocation): WeatherByLocationResponse {
        if (cache.containsKey(geoLocation)) {
            return cache.get(geoLocation)!!
        } else {
            val response = apiHelper.getWeather(geoLocation)
            cache[geoLocation] = response
            return response
        }
    }
}
