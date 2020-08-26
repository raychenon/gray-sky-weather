package io.betterapps.graysky

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.betterapps.graysky.ui.weatherforecast.WeatherForecastFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            val ft = supportFragmentManager.beginTransaction()
            ft.add(
                R.id.main_container,
                WeatherForecastFragment.newInstance("Berlin", 52.520007, 13.404954),
                "Berlin"
            )
            ft.add(
                R.id.main_container,
                WeatherForecastFragment.newInstance("Paris", 48.866667, 2.333333),
                "Paris"
            )
            ft.add(
                R.id.main_container,
                WeatherForecastFragment.newInstance("Santa Clara", 37.3997, -121.9608),
                "Santa Clara"
            )
            ft.commitNow()
        }
    }
}
