package io.betterapps.graysky.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.betterapps.graysky.data.models.WeatherByLocationResponse
import io.betterapps.graysky.data.models.WeatherUnit

/**
 * This adapter data won't be updated.
 * Hence the data are passed by constructor
 */
class HourlyWeatherAdapter(val response: WeatherByLocationResponse) :
    RecyclerView.Adapter<HourlyWeatherViewHolder>() {

    var data: List<WeatherUnit>

    init {
        data = response.hourly
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyWeatherViewHolder {
        return HourlyWeatherViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: HourlyWeatherViewHolder, position: Int) {
        holder.apply {
            bindData(data.get(position), response.timezoneOffset)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
