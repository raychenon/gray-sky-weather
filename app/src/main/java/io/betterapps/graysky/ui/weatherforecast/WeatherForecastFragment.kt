package io.betterapps.graysky.ui.weatherforecast

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

class WeatherForecastFragment : Fragment() {

    companion object {
        const val ARG_LOCATION = "location_name"
        const val ARG_LATITUDE = "latitude"
        const val ARG_LONGITUDE = "longitude"
        const val ARG_DISTANCE = "distance"

        fun newInstance(
            name: String,
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

    lateinit var locationName: String
    lateinit var geolocation: GeoLocation
    var distanceFromUserLocation: Double = 0.0

    // lazy inject
    val weatherViewModel: WeatherViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        locationName = arguments?.getString(ARG_LOCATION)!!
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
        assertNotNull(locationName)

        forcecast_weather_location_textview.text =
            getString(R.string.location_format, locationName, distanceFromUserLocation.toInt())

        weatherViewModel.requestWeatherByLocation(geolocation)
            .observe(
                viewLifecycleOwner,
                androidx.lifecycle.Observer {
                    it?.let { resource ->
                        processWeatherResults(resource)
                    }
                }
            )
    }

    private fun processWeatherResults(
        resource: Resource<WeatherByLocationResponse>
    ) {
        when (resource.status) {
            Status.LOADING -> {
                forcecast_weather_progressbar.visibility = View.VISIBLE
                Timber.d("Weather LOADING")
            }
            Status.SUCCESS -> {
                forcecast_weather_progressbar.visibility = View.INVISIBLE
                Timber.d("Weather Success ${resource.data}")
                setupUI(response = resource.data!!)
            }
            Status.ERROR -> {
                forcecast_weather_progressbar.visibility = View.INVISIBLE
            }
        }
    }

    private fun setupUI(response: WeatherByLocationResponse) {
        // add divider
        val dividerItemDecoration = DividerItemDecoration(
            forcecast_weather_recyclerview.context,
            LinearLayout.HORIZONTAL
        )
        forcecast_weather_recyclerview.addItemDecoration(dividerItemDecoration)

        val horizontalLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val weatherAdapter = HourlyWeatherAdapter(response)
        forcecast_weather_recyclerview.apply {
            adapter = weatherAdapter
            layoutManager = horizontalLayoutManager
        }
    }
}
