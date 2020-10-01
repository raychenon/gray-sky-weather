package io.betterapps.graysky.repository

import io.betterapps.graysky.data.db.entities.LocationEntity
import io.betterapps.graysky.data.domains.LocationName

interface LocationRepository {

    var cache: MutableList<LocationName>

    fun initialize()

    fun addLocation(locationEntity: LocationEntity): Unit

    fun deleteLocation(name: String): Unit

    suspend fun retrieveLocations(): List<LocationName>
}