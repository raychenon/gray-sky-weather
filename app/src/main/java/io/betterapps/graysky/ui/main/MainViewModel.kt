package io.betterapps.graysky.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import io.betterapps.graysky.data.coroutines.Resource
import io.betterapps.graysky.data.models.GeoLocation
import io.betterapps.graysky.data.models.WeatherByLocationResponse
import io.betterapps.graysky.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers

class MainViewModel(val repository: WeatherRepository) : ViewModel() {
    // TODO: Implement the ViewModel

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
}
