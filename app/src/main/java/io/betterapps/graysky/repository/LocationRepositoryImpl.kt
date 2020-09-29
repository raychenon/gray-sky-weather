package io.betterapps.graysky.repository

import io.betterapps.graysky.data.db.entities.LocationDao
import io.betterapps.graysky.data.db.entities.LocationEntity
import io.betterapps.graysky.data.domains.GeoLocation
import io.betterapps.graysky.data.domains.LocationName

class LocationRepositoryImpl(private val dao: LocationDao) : LocationRepository {

    override suspend fun addLocation(locationEntity: LocationEntity) {
        dao.insert(locationEntity)
    }

    override suspend fun deleteLocation(name: String) {
        dao.deleteCity(name)
    }

    override fun retrieveLocations(): List<LocationName> {
        return dao.getLocations()
            .map { it -> LocationName(it.name, GeoLocation(it.latitude, it.longitude)) }
    }
}