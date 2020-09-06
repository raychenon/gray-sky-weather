package io.betterapps.graysky.const

import io.betterapps.graysky.data.domains.GeoLocation
import io.betterapps.graysky.data.domains.LocationName

object GlobalConstants {

    const val BASE_URL = "https://api.openweathermap.org/"

    const val API_ID = "3e20bab8cf7b31f1aadb98d108f23153"
    // const val API_ID = "123ecab62dd22e2ba983c28b73c630cb" // 2nd key, if you reached the limit

    const val MAPBOX_API = "pk.eyJ1IjoicmF5bW9uZC1jaGVub24tYXJiZWl0IiwiYSI6ImNrZXIyZHZ1YTQ5YXoyemx0c3FjYWRrbG8ifQ.o3-GihSAvLkNSTH2ENk1Cw"

    val CITIES = listOf<LocationName>(
        LocationName("Paris", GeoLocation(48.8534, 2.3488)),
        LocationName("Gravelines", GeoLocation(50.9833, 2.1167)),
        LocationName("Amsterdam", GeoLocation(52.370216, 4.895168)),
        LocationName("Chicago", GeoLocation(41.8119, -87.6873)),
        LocationName("Dallas", GeoLocation(32.9503, -96.819)),
        LocationName("New York", GeoLocation(40.7808, -73.9772)),
        LocationName("Santa Clara", GeoLocation(37.3997, -121.9608))
    )

    val USER_LOCATION = GeoLocation(52.520007, 13.404954) // Berlin
}
