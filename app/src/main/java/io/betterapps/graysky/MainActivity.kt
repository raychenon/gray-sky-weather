package io.betterapps.graysky

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import es.dmoral.toasty.Toasty
import io.betterapps.graysky.const.GlobalConstants
import io.betterapps.graysky.data.domains.GeoLocation
import io.betterapps.graysky.data.domains.LocationName
import io.betterapps.graysky.geoloc.UserLocationDelegate
import io.betterapps.graysky.ui.main.MainViewModel
import io.betterapps.graysky.ui.weatherforecast.WeatherForecastFragment
import kotlinx.android.synthetic.main.main_activity.*
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
                userLocationDelegate = UserLocationDelegate(this)
                if (savedInstanceState == null) {
                    launchPermission()
                }
            } else {
                displayWeatherFromLocations(GlobalConstants.USER_LOCATION)
            }
        }


        // Initialize the SDK
        Places.initialize(applicationContext, getString(R.string.cloud_platform_api))

        // Create a new PlacesClient instance
        val placesClient: PlacesClient = Places.createClient(this)

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
                    displayWeatherOrError(loc)
                }
            )
        }
    }

    private val AUTOCOMPLETE_REQUEST_CODE = 1

    private fun launchAutocomplete(): Unit {

        // Set the fields to specify which types of place data to
        // return after the user has made a selection.
        val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)

        // Start the autocomplete intent.
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
            .build(this)
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        Timber.i("onActivityResult Place: ${place.name}, ${place.id}, ${place.latLng} , ${place.toString()}")
                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    // TODO: Handle the error.
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                        Timber.i("onActivityResult ${status.statusMessage}")
                    }
                }
                Activity.RESULT_CANCELED -> {
                    Timber.i("onActivityResult cancelled")
                }
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_add -> {
            launchAutocomplete()
            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    private fun showRationale() {
        Toasty.error(this, R.string.permission_rationale, Toast.LENGTH_SHORT, true).show()
    }

    private fun showErrorLastLocation() {
        Toasty.error(this, R.string.permission_last_location_not_found, Toast.LENGTH_LONG, true)
            .show()
    }

    private fun displayWeatherOrError(location: Location?) {

        location?.let {
            displayWeatherFromLocations(location2Geolocation(it))
        } ?: run {
            showErrorLastLocation()
        }
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
            { loc -> displayWeatherOrError(loc) }
        )
    }
}
