package io.betterapps.graysky.repository

import io.betterapps.graysky.data.db.entities.LocationEntity
import io.betterapps.graysky.data.domains.LocationName

interface LocationRepository {

    suspend fun addLocation(locationEntity: LocationEntity): Unit

    suspend fun deleteLocation(name: String): Unit

    fun retrieveLocations(): List<LocationName>
}