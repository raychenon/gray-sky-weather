package io.betterapps.graysky.data.binding

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt

data class ObservableWeather(
    val progressStatus: ObservableInt,
    var errorMsg: ObservableField<String?>
)