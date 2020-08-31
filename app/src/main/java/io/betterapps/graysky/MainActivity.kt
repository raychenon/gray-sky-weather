package io.betterapps.graysky

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import es.dmoral.toasty.Toasty
import io.betterapps.graysky.const.GlobalConstants
import io.betterapps.graysky.data.domains.GeoLocation
import io.betterapps.graysky.data.domains.Location
import io.betterapps.graysky.geoloc.UserLocationDelegate
import io.betterapps.graysky.ui.main.MainViewModel
import io.betterapps.graysky.ui.weatherforecast.WeatherForecastFragment
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    // lazy inject
    val mainViewModel: MainViewModel by viewModel()

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var userLocationDelegate: UserLocationDelegate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = getFusedLocationProviderClient(this)
        userLocationDelegate = UserLocationDelegate(this, this)

        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            launchPermission()
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

    private fun launchPermission() {
        if (!userLocationDelegate.checkPermissions()) {
            userLocationDelegate.requestPermissions(::showRationale)
        } else {
            getLastLocation()
        }
    }

    private fun showRationale() {
        Toasty.error(this, R.string.permission_rationale, Toast.LENGTH_SHORT, true).show()
    }

    private fun getLastLocation() {
        fusedLocationClient.lastLocation
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful && task.result != null) {
                    // Got last known location. In some rare situations this can be null.
                    task?.result?.let {
                        mainViewModel.sortByDistance(
                            GeoLocation(it.latitude, it.longitude),
                            GlobalConstants.CITIES
                        )
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
                }
            }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        Timber.i("onRequestPermissionResult")
        if (requestCode == userLocationDelegate.REQUEST_PERMISSIONS_REQUEST_CODE) {
            when {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                grantResults.isEmpty() -> Timber.i("User interaction was cancelled.")

                // Permission granted.
                (grantResults[0] == PackageManager.PERMISSION_GRANTED) -> getLastLocation()
            }
        }
    }
}
