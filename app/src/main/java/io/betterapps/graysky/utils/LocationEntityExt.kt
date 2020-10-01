package io.betterapps.graysky.utils

import io.betterapps.graysky.data.db.entities.LocationEntity
import io.betterapps.graysky.data.domains.GeoLocation
import io.betterapps.graysky.data.domains.LocationName

fun LocationEntity.toLocationName(): LocationName {
    return LocationName(this.name, GeoLocation(this.latitude, this.longitude))
}