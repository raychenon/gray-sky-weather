package io.betterapps.graysky.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import io.betterapps.graysky.data.db.entities.LocationEntity
import io.betterapps.graysky.data.domains.GeoLocation
import io.betterapps.graysky.data.domains.LocationName
import io.betterapps.graysky.repository.LocationRepository
import io.betterapps.graysky.utils.distance
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val locationRepository: LocationRepository) : ViewModel() {

    fun initialize() {
        locationRepository.initialize()
    }

    fun sortByDistance(
        currentGeoLocation: GeoLocation
    ): LiveData<List<LocationName>> =
        liveData(Dispatchers.IO) {

            val list = mutableListOf<Pair<Int, Double>>()
            for (i in 0 until locationRepository.cache.size) {
                list.add(Pair(i, currentGeoLocation.distance(locationRepository.cache[i].geoLocation)))
            }
            list.sortBy { p -> p.second }

            val locationsSorted = mutableListOf<LocationName>()
            locationsSorted.add(LocationName(null, currentGeoLocation, 0.0))
            for (i in 0 until locationRepository.cache.size) {
                val index = list[i].first
                locationsSorted.add(locationRepository.cache[index].copy(distanceInKm = list[i].second))
            }
            emit(locationsSorted)
        }

    fun addLocation(locationEntity: LocationEntity): Unit {
        locationRepository.addLocation(locationEntity)
    }

    fun deleteLocation(cityName: String): Unit {
        locationRepository.deleteLocation(cityName)
    }
}
