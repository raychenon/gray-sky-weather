package io.betterapps.graysky.utils

import io.betterapps.graysky.data.domains.GeoLocation

object DistanceUtils {

    private const val EARTH_RADIUS = 6371

    /**
     *  Haversine formula
     *  return distance in km
     */
    @JvmStatic
    fun distance(
        start: GeoLocation,
        end: GeoLocation
    ): Double {
        var startLat = start.latitude
        var endLat = end.latitude
        val dLat = Math.toRadians(endLat - startLat)
        val dLong = Math.toRadians(end.longitude - start.longitude)
        startLat = Math.toRadians(startLat)
        endLat = Math.toRadians(endLat)
        val a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return EARTH_RADIUS * c
    }

    @JvmStatic
    fun haversin(value: Double): Double {
        return Math.pow(Math.sin(value / 2), 2.0)
    }
}
