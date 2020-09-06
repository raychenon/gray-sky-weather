package io.betterapps.graysky.ui.start

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import io.betterapps.graysky.MainActivity
import io.betterapps.graysky.R

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.start_activity)

        val geolocationButton = findViewById<Button>(R.id.start_geolocation_button)
        val noGeolocButton = findViewById<Button>(R.id.start_no_location_button)

        geolocationButton.setOnClickListener({
            goToMainActivity(true)
        })

        noGeolocButton.setOnClickListener({
            goToMainActivity(false)
        })
    }

    private fun goToMainActivity(locationEnabled: Boolean) {
        val intent = MainActivity.createIntent(this, locationEnabled)
        startActivity(intent)
    }
}
