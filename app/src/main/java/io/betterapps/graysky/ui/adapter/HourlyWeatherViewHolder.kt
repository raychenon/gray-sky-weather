package io.betterapps.graysky.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import io.betterapps.graysky.R
import io.betterapps.graysky.data.models.WeatherUnit
import io.betterapps.graysky.ui.image.ImageLoader
import kotlinx.android.extensions.LayoutContainer

class HourlyWeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer {

    companion object {
        fun from(parent: ViewGroup): HourlyWeatherViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.weather_hourly_viewholder, parent, false)
            return HourlyWeatherViewHolder(view)
        }
    }

    val timeTextView: TextView = itemView.findViewById(R.id.weather_hour_time_textview)
    val realTempTextView: TextView = itemView.findViewById(R.id.weather_hour_real_temp_textview)
    val dateTextView: TextView = itemView.findViewById(R.id.weather_date_textview)
    val rainTextView: TextView =
        itemView.findViewById(R.id.weather_hour_rain_textview)
    val iconView: ImageView = itemView.findViewById(R.id.weather_icon_imageview)

    override val containerView: View?
        get() = itemView

    fun bindData(item: WeatherUnit, timezoneOffset: Long) {
        timeTextView.text = item.actualLocalTime(timeOffset = timezoneOffset)
        realTempTextView.text = formatTemperature(item.temperature)
        item.rain?.let {
            rainTextView.visibility = View.VISIBLE
            // todo find a better a wording for rain
            rainTextView.text =
                getString(R.string.rain_volume_format, it.precipitationVolumeForecast1h)
        } ?: run {
            rainTextView.visibility = View.GONE
        }
        ImageLoader.load(iconView, item.iconURL())

        if (item.isBeforeNewDay(timezoneOffset)) {
            dateTextView.visibility = View.VISIBLE
            dateTextView.text = item.formatNextDay(timezoneOffset)
        } else {
            dateTextView.visibility = View.GONE
        }
    }

    private fun formatTemperature(valFloat: Float): String {
        return getString(R.string.temperature_celsius_format, valFloat)
    }

    private fun getString(@StringRes resId: Int, valFloat: Float): String {
        return itemView.context.getString(resId, valFloat)
    }
}
