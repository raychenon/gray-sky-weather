package io.betterapps.graysky.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import io.betterapps.graysky.data.domains.GeoLocation
import io.betterapps.graysky.data.domains.LocationName
import io.betterapps.graysky.utils.distance
import kotlinx.coroutines.Dispatchers

class MainViewModel : ViewModel() {

    fun sortByDistance(
        currentGeoLocation: GeoLocation,
        locationNames: List<LocationName>
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
}
