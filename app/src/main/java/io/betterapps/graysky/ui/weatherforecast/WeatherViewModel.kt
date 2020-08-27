package io.betterapps.graysky.ui.weatherforecast

import android.location.Geocoder
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import io.betterapps.graysky.data.coroutines.Resource
import io.betterapps.graysky.data.domains.GeoLocation
import io.betterapps.graysky.data.models.WeatherByLocationResponse
import io.betterapps.graysky.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import java.io.IOException

class WeatherViewModel(val repository: WeatherRepository) : ViewModel() {

    fun requestWeatherByLocation(
        geoLocation: GeoLocation
    ): LiveData<Resource<WeatherByLocationResponse>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))
            try {
                emit(Resource.success(repository.getWeatherByLocation(geoLocation)))
            } catch (exception: Exception) {
                emit(Resource.error(data = null, message = exception.message ?: "Error"))
            }
        }

    /**
     * https://stackoverflow.com/questions/472313/android-reverse-geocoding-getfromlocation/4981063#4981063
     */
    fun requestCityName(
        geocoder: Geocoder,
        geoLocation: GeoLocation
    ): LiveData<String> = liveData(Dispatchers.IO) {
        var name: String? = null
        try {
            val addresses = geocoder.getFromLocation(geoLocation.latitude, geoLocation.longitude, 1)

            if (addresses != null && addresses.size > 0) {
                name = addresses[0].toString()
            } else {
                emit("No found")
            }
        } catch (e: IOException) {
            emit("Error ")
            // Log.e(TAG, "Impossible to connect to Geocoder", e)
            // Timber.e("error", e)
        } finally {
            name?.let { emit(it) } ?: emit("error")
        }
    }
}
