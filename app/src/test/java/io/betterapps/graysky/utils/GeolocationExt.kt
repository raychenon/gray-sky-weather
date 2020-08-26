package io.betterapps.graysky.utils

import io.betterapps.graysky.data.domains.GeoLocation

fun GeoLocation.distance(position: GeoLocation): Double {
    return DistanceUtils.distance(this, position)
}
