package io.betterapps.graysky.const

import io.betterapps.graysky.data.domains.GeoLocation
import io.betterapps.graysky.data.domains.Location

object GlobalConstants {

    const val BASE_URL = "https://api.openweathermap.org/"

    const val API_ID = "3e20bab8cf7b31f1aadb98d108f23153"
    //const val API_ID = "123ecab62dd22e2ba983c28b73c630cb" // 2nd key, if you reached the limit

    val CITIES = listOf<Location>(
        Location("Paris", GeoLocation(48.8534, 2.3488)),
        Location("Gravelines", GeoLocation(50.9833, 2.1167)),
        Location("Amsterdam", GeoLocation(52.370216, 4.895168)),
        Location("Chicago", GeoLocation(41.8119, -87.6873)),
        Location("Dallas", GeoLocation(32.9503, -96.819)),
        Location("New York", GeoLocation(40.7808, -73.9772)),
        Location("Santa Clara", GeoLocation(37.3997, -121.9608))
    )

    val USER_LOCATION = GeoLocation(52.520007, 13.404954) // Berlin
}
