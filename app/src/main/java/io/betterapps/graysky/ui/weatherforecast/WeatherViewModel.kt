package io.betterapps.graysky.ui.weatherforecast

import android.location.Geocoder
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import io.betterapps.graysky.data.coroutines.Resource
import io.betterapps.graysky.data.domains.GeoLocation
import io.betterapps.graysky.data.models.WeatherByLocationResponse
import io.betterapps.graysky.repository.LocationRepository
import io.betterapps.graysky.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import timber.log.Timber
import java.io.IOException

class WeatherViewModel(val weatherRepository: WeatherRepository) : ViewModel() {

    fun requestWeatherByLocation(
        geoLocation: GeoLocation
    ): LiveData<Resource<WeatherByLocationResponse>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))
            try {
                emit(Resource.success(weatherRepository.getWeatherByLocation(geoLocation)))
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
        try {
            val addresses = geocoder.getFromLocation(geoLocation.latitude, geoLocation.longitude, 1)
            Timber.d("Geocoder try $addresses")
            if (addresses != null && addresses.size > 0) {
                val cityName = addresses[0].getAddressLine(0)
                cityName?.let { emit(it) } ?: emit("error")
            } else {
                emit("No found")
            }
        } catch (e: IOException) {
            Timber.e("Geocoder exception $e")
            emit("Error ")
        }
    }
}
