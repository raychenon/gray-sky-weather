package io.betterapps.graysky.data.network

import io.betterapps.graysky.data.domains.GeoLocation
import retrofit2.http.GET
import retrofit2.http.Path

interface MapBox {
    // https://api.mapbox.com/geocoding/v5/mapbox.places/Paris.json?access_token=pk.eyJ1IjoicmF5bW9uZC1jaGVub24tYXJiZWl0IiwiYSI6ImNrZXIyZHZ1YTQ5YXoyemx0c3FjYWRrbG8ifQ.o3-GihSAvLkNSTH2ENk1Cw
    // features > center

    @GET("/geocoding/v5/mapbox.places/{city}.json")
    suspend fun getGeolocation(@Path("city") city: String): GeoLocation
}