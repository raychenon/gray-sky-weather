package io.betterapps.graysky.data.network

import io.betterapps.graysky.data.models.WeatherByLocationResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface OpenWeatherMapService {

    // https://api.openweathermap.org/data/2.5/onecall?lat=33.441792&lon=-94.037689&units=metric&appid=3e20bab8cf7b31f1aadb98d108f23153
    @GET("/data/2.5/onecall")
    suspend fun getForecast(
        @Query("lat") lat: Double,
        @Query("lon") longitude: Double,
        @QueryMap options: Map<String, String>
    ): WeatherByLocationResponse

}