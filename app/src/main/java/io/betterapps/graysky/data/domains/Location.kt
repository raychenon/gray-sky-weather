package io.betterapps.graysky.data.domains

data class Location(
    val name: String,
    val geoLocation: GeoLocation,
    var distanceInKm: Double = 0.0
)
