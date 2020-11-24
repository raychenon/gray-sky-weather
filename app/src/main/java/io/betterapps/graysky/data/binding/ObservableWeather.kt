package io.betterapps.graysky.data.binding

import io.betterapps.graysky.data.coroutines.Status

data class ObservableWeather(
    var progressStatus: Status,
    var errorMsg: String?
)