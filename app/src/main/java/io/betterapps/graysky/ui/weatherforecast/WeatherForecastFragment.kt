package io.betterapps.graysky.ui.weatherforecast

import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.betterapps.graysky.R
import io.betterapps.graysky.data.coroutines.Resource
import io.betterapps.graysky.data.coroutines.Status
import io.betterapps.graysky.data.domains.GeoLocation
import io.betterapps.graysky.data.models.WeatherByLocationResponse
import io.betterapps.graysky.ui.adapter.HourlyWeatherAdapter
import kotlinx.android.synthetic.main.forecast_weather_fragment.*
import org.junit.Assert.assertNotNull
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.Locale

class WeatherForecastFragment : Fragment() {

    companion object {
        const val ARG_LOCATION = "location_name"
        const val ARG_LATITUDE = "latitude"
        const val ARG_LONGITUDE = "longitude"
        const val ARG_DISTANCE = "distance"

        fun newInstance(
            name: String?,
            latitude: Double,
            longitude: Double,
            distanceFromUserLocation: Double
        ): WeatherForecastFragment {
            val fragment = WeatherForecastFragment()

            val bundle = Bundle().apply {
                putString(ARG_LOCATION, name)
                putDouble(ARG_LATITUDE, latitude)
                putDouble(ARG_LONGITUDE, longitude)
                putDouble(ARG_DISTANCE, distanceFromUserLocation)
            }

            fragment.arguments = bundle

            return fragment
        }
    }

    lateinit var geolocation: GeoLocation
    var distanceFromUserLocation: Double = 0.0
    var locationName: String? = null

    // lazy inject
    val weatherViewModel: WeatherViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        locationName = arguments?.getString(ARG_LOCATION)
        geolocation = GeoLocation(
            arguments?.getDouble(ARG_LATITUDE)!!,
            arguments?.getDouble(ARG_LONGITUDE)!!
        )
        distanceFromUserLocation = arguments?.getDouble(ARG_DISTANCE)!!

        return inflater.inflate(R.layout.forecast_weather_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // since Koin DI is done at run time instead of compile time, better to check
        assertNotNull(weatherViewModel)
        assertNotNull(weatherViewModel.repository)
        assertNotNull(geolocation)

        weatherViewModel.requestWeatherByLocation(geolocation)
            .observe(
                viewLifecycleOwner,
                androidx.lifecycle.Observer {
                    it?.let { resource ->
                        processWeatherResults(resource)
                    }
                }
            )

        locationName?.let {
            displayCityName(it, distanceFromUserLocation.toInt())
        } ?: run {
            if (Geocoder.isPresent()) {
                val geocoder = Geocoder(context, Locale.US)
                weatherViewModel.requestCityName(geocoder, geolocation)
                    .observe(
                        viewLifecycleOwner,
                        androidx.lifecycle.Observer {
                            it?.let { cityName ->
                                Timber.i("Geocoder livedata received $cityName")
                                displayCityName(cityName, distanceFromUserLocation.toInt())
                            }
                        }
                    )
            } else {
                displayCityName("No geocoder", distanceFromUserLocation.toInt())
            }
        }
    }

    private fun displayCityName(cityName: String, distanceFromUserLocation: Int) {
        forecast_weather_location_textview.text =
            getString(R.string.location_format, cityName, distanceFromUserLocation)
    }

    private fun processWeatherResults(
        resource: Resource<WeatherByLocationResponse>
    ) {
        when (resource.status) {
            Status.LOADING -> {
                forecast_weather_progressbar.visibility = View.VISIBLE
            }
            Status.SUCCESS -> {
                forecast_weather_progressbar.visibility = View.INVISIBLE
                forecast_weather_error_textview.visibility = View.GONE

                resource.data?.let { setupUI(it) }
            }
            Status.ERROR -> {
                forecast_weather_progressbar.visibility = View.INVISIBLE
                forecast_weather_error_textview.visibility = View.VISIBLE
                forecast_weather_error_textview.text = resource.message
            }
        }
    }

    private fun setupUI(response: WeatherByLocationResponse) {
        // add divider
        val dividerItemDecoration = DividerItemDecoration(
            forecast_weather_recyclerview.context,
            LinearLayout.HORIZONTAL
        )
        forecast_weather_recyclerview.addItemDecoration(dividerItemDecoration)

        val horizontalLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val weatherAdapter = HourlyWeatherAdapter(response)
        forecast_weather_recyclerview.apply {
            adapter = weatherAdapter
            layoutManager = horizontalLayoutManager
        }
    }
}
