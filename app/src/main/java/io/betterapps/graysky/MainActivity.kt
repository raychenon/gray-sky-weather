package io.betterapps.graysky

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.betterapps.graysky.const.GlobalConstants
import io.betterapps.graysky.data.domains.GeoLocation
import io.betterapps.graysky.data.domains.Location
import io.betterapps.graysky.ui.main.MainViewModel
import io.betterapps.graysky.ui.weatherforecast.WeatherForecastFragment
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    // lazy inject
    val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            mainViewModel.sortByDistance(GeoLocation(52.520007, 13.404954), GlobalConstants.cities)
                .observe(
                    this,
                    androidx.lifecycle.Observer { it?.let { locations -> showWeatherFragments(locations) } }
                )
        }
    }

    fun showWeatherFragments(locations: List<Location>) {
        val ft = supportFragmentManager.beginTransaction()
        for (location in locations) {
            ft.add(
                R.id.main_container,
                WeatherForecastFragment.newInstance(
                    location.name,
                    location.geoLocation.latitude,
                    location.geoLocation.longitude,
                    location.distanceInKm
                ),
                location.name
            )
        }
        ft.commitNow()
    }
}
