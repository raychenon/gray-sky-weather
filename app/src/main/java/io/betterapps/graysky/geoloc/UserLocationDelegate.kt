package io.betterapps.graysky.geoloc

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import timber.log.Timber

/**
 * self contained class to get location permission and the user current location
 */
class UserLocationDelegate {

    companion object {
        val REQUEST_PERMISSIONS_REQUEST_CODE = 35
    }

    val MANIFEST_ACCESS_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION

    private val context: Context
    private val activity: Activity
    private val fusedLocationClient: FusedLocationProviderClient

    constructor(activity: Activity) {
        this.activity = activity
        this.context = activity as Context
        fusedLocationClient = FusedLocationProviderClient(activity)
    }

    fun requestPermissions(showRationale: () -> Unit) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, MANIFEST_ACCESS_LOCATION)) {
            // Provide an additional rationale to the user. This would happen if the user denied the
            // request previously, but didn't check the "Don't ask again" checkbox.
            Timber.i("Displaying permission rationale to provide additional context.")
            showRationale()
        } else {
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            Timber.i("Requesting permission")
            startLocationPermissionRequest()
        }
    }

    fun getLastLocation(geoListener: (loc: Location) -> Unit) {
        checkLocationPermission(context)
        Timber.i("getLastLocation ")
        // TODO on emulator , last location is null
        fusedLocationClient?.lastLocation
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful && task.result != null) {
                    // Got last known location. In some rare situations this can be null.
                    task?.result?.let { it -> geoListener(it) }
                }
            }
    }

    /**
     * Return the current state of the permissions needed.
     */
    fun checkLocationPermission(context: Context?): Boolean {
        return (ActivityCompat.checkSelfPermission(context!!, MANIFEST_ACCESS_LOCATION) == PackageManager.PERMISSION_GRANTED)
    }

    fun onRequestPermissionsResultDelete(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
        geoListener: (loc: Location) -> Unit
    ) {
        Timber.i("onRequestPermissionResult")
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                getLastLocation(geoListener)
            } else {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Timber.i("User interaction was cancelled.")
            }
        }
    }

    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(MANIFEST_ACCESS_LOCATION),
            REQUEST_PERMISSIONS_REQUEST_CODE
        )
    }
}
