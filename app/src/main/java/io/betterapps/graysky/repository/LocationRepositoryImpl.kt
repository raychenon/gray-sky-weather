package io.betterapps.graysky.repository

import io.betterapps.graysky.const.GlobalConstants
import io.betterapps.graysky.data.db.entities.LocationDao
import io.betterapps.graysky.data.db.entities.LocationEntity
import io.betterapps.graysky.data.domains.GeoLocation
import io.betterapps.graysky.data.domains.LocationName
import io.betterapps.graysky.utils.toLocationName
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class LocationRepositoryImpl(private val dao: LocationDao,
    override var locationNames: MutableList<LocationName> = mutableListOf()
) : LocationRepository {


    init {
        locationNames = mutableListOf()
        locationNames.addAll(GlobalConstants.CITIES)
    }

    override fun initialize() {
        CoroutineScope(CoroutineName("init")).launch {
            locationNames.addAll(retrieveLocations())
        }
    }

    override fun addLocation(locationEntity: LocationEntity): Unit {
        locationNames.add(locationEntity.toLocationName())
        CoroutineScope(CoroutineName("add")).launch {
            dao.insert(locationEntity)
        }
    }

    override fun deleteLocation(name: String) {
        locationNames.remove(name)
        CoroutineScope(CoroutineName("delete")).launch {
            dao.deleteCity(name)
        }
    }

    override suspend fun retrieveLocations(): List<LocationName> {
        // val mutableList = mutableListOf<LocationName>()
        // mutableList.addAll(dao.getLocations()
        //     .map { it -> LocationName(it.name, GeoLocation(it.latitude, it.longitude)) })

        return dao.getLocations()
            .map { it -> LocationName(it.name, GeoLocation(it.latitude, it.longitude)) }
    }
}