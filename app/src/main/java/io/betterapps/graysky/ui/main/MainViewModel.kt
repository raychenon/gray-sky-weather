package io.betterapps.graysky.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import io.betterapps.graysky.const.GlobalConstants
import io.betterapps.graysky.data.db.entities.LocationEntity
import io.betterapps.graysky.data.domains.GeoLocation
import io.betterapps.graysky.data.domains.LocationName
import io.betterapps.graysky.repository.LocationRepository
import io.betterapps.graysky.utils.distance
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val locationRepository: LocationRepository) : ViewModel() {

    private lateinit var locationNames: MutableList<LocationName>

    init {
        locationNames = mutableListOf()
        locationNames.addAll(GlobalConstants.CITIES)
    }

    fun initialize(){
        locationNames.addAll(getLocations())
    }

    fun sortByDistance(
        currentGeoLocation: GeoLocation
    ): LiveData<List<LocationName>> =
        liveData(Dispatchers.IO) {

            val list = mutableListOf<Pair<Int, Double>>()
            for (i in 0 until locationNames.size) {
                list.add(Pair(i, currentGeoLocation.distance(locationNames[i].geoLocation)))
            }
            list.sortBy { p -> p.second }

            val locationsSorted = mutableListOf<LocationName>()
            locationsSorted.add(LocationName(null, currentGeoLocation, 0.0))
            for (i in 0 until locationNames.size) {
                val index = list[i].first
                locationsSorted.add(locationNames[index].copy(distanceInKm = list[i].second))
            }
            emit(locationsSorted)
        }

    fun addLocation(locationEntity: LocationEntity): Unit {
        CoroutineScope(CoroutineName("add")).launch {
            locationRepository.addLocation(locationEntity)
        }
    }

    fun deleteLocation(cityName: String): Unit {
        CoroutineScope(CoroutineName("delete")).launch {
            locationRepository.deleteLocation(cityName)
        }
    }

    fun getLocations(): List<LocationName> {
        return locationRepository.retrieveLocations()
    }
}
