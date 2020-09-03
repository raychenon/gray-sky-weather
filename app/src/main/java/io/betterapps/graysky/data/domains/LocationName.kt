package io.betterapps.graysky.data.domains

data class LocationName(
    val name: String?, // nullable means no location name
    val geoLocation: GeoLocation,
    var distanceInKm: Double = 0.0
)
