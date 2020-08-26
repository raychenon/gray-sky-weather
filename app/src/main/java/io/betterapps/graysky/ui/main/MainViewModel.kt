package io.betterapps.graysky.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import io.betterapps.graysky.data.coroutines.Resource
import io.betterapps.graysky.data.domains.GeoLocation
import io.betterapps.graysky.data.domains.Location
import io.betterapps.graysky.data.models.WeatherByLocationResponse
import io.betterapps.graysky.repository.WeatherRepository
import io.betterapps.graysky.utils.distance
import kotlinx.coroutines.Dispatchers

class MainViewModel(val repository: WeatherRepository) : ViewModel() {

    fun requestWeatherByLocation(
        geoLocation: GeoLocation
    ): LiveData<Resource<WeatherByLocationResponse>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))
            try {
                emit(Resource.success(repository.getWeatherByLocation(geoLocation)))
            } catch (exception: Exception) {
                emit(Resource.error(data = null, message = exception.message ?: "Miss Iganes"))
            }
        }

    fun sortByDistance(
        currentGeoLocation: GeoLocation,
        locations: List<Location>
    ): LiveData<List<Location>> =
        liveData(Dispatchers.IO) {

            val list = mutableListOf<Pair<Int, Double>>()
            for (i in 0 until locations.size) {
                list.add(Pair(i, currentGeoLocation.distance(locations[i].geoLocation)))
            }
            list.sortBy { p -> p.second }

            val locationsSorted = mutableListOf<Location>()
            locationsSorted.add(Location("current", currentGeoLocation, 0.0))
            for (i in 0 until locations.size) {
                val index = list[i].first
                locationsSorted.add(locations[index].copy(distanceInKm = list[i].second))
            }
            emit(locationsSorted)
        }
}
