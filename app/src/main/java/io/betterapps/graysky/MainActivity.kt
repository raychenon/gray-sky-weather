package io.betterapps.graysky

import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import es.dmoral.toasty.Toasty
import io.betterapps.graysky.const.GlobalConstants
import io.betterapps.graysky.data.domains.GeoLocation
import io.betterapps.graysky.data.domains.LocationName
import io.betterapps.graysky.geoloc.UserLocationDelegate
import io.betterapps.graysky.ui.main.MainViewModel
import io.betterapps.graysky.ui.weatherforecast.WeatherForecastFragment
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    // lazy inject
    val mainViewModel: MainViewModel by viewModel()

    private lateinit var userLocationDelegate: UserLocationDelegate

    companion object {
        private val BUNDLE_GEOLOC = "GEOLOC_EXTRA"

        fun createIntent(context: Context, geolocEnabled: Boolean): Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(BUNDLE_GEOLOC, geolocEnabled)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)
        val extras = intent.extras
        extras?.let {
            val enabled = it.getBoolean(BUNDLE_GEOLOC)
            if (enabled) {
                userLocationDelegate = UserLocationDelegate(this, this)
                if (savedInstanceState == null) {
                    launchPermission()
                }
            } else {
                displayWeatherFromLocations(GlobalConstants.USER_LOCATION)
            }
        }
    }

    fun showWeatherFragments(locations: List<LocationName>) {
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

    private fun launchPermission() {
        if (!userLocationDelegate.checkLocationPermission(this)) {
            userLocationDelegate.requestPermissions(::showRationale)
        } else {
            userLocationDelegate.getLastLocation(
                { loc ->
                    displayWeatherFromLocations(location2Geolocation(loc))
                }
            )
        }
    }

    private fun showRationale() {
        Toasty.error(this, R.string.permission_rationale, Toast.LENGTH_SHORT, true).show()
    }

    private fun displayWeatherFromLocations(currentUserlocation: GeoLocation) {
        mainViewModel.sortByDistance(currentUserlocation, GlobalConstants.CITIES)
            .observe(
                this,
                androidx.lifecycle.Observer {
                    it?.let { locations ->
                        showWeatherFragments(
                            locations
                        )
                    }
                }
            )
    }

    private fun location2Geolocation(location: Location): GeoLocation =
        GeoLocation(location.latitude, location.longitude)

    /**
     * Callback received when a permissions request has been completed.
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        Timber.i("onRequestPermissionResult")
        userLocationDelegate.onRequestPermissionsResultDelete(
            requestCode,
            permissions,
            grantResults,
            { loc -> displayWeatherFromLocations(location2Geolocation(loc)) }
        )
    }
}
