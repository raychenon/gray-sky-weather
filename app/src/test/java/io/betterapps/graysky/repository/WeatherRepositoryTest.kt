package io.betterapps.graysky.repository

import com.nhaarman.mockitokotlin2.mock
import io.betterapps.graysky.data.api.ApiHelper
import io.betterapps.graysky.data.domains.GeoLocation
import io.betterapps.graysky.data.models.WeatherByLocationResponse
import io.betterapps.graysky.data.models.WeatherUnit
import io.betterapps.graysky.data.network.RetrofitFactory
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.Assert
import org.junit.Test

class WeatherRepositoryTest {

    private lateinit var mockApiHelper: ApiHelper

    @Test
    fun loadFromCache() {
        val okHttp = mock<OkHttpClient>()
        val openWeatherMapService = RetrofitFactory.weatherService(okHttp)
        mockApiHelper = ApiHelper(openWeatherMapService) // must be mocked

        // https://github.com/nhaarman/mockito-kotlin/issues/302#issuecomment-434218706
        // mock crashes at init
        // mockApiHelper = mock(ApiHelper::class.java)

        val entry = GeoLocation(48.8534, 2.3488)
        val weatherUnit = WeatherUnit(1598454000, 18.13f, 12.59f, 72, 75, null, emptyList())
        val response =
            WeatherByLocationResponse("", -25200L, weatherUnit, listOf(weatherUnit), emptyList())

        val cache = mutableMapOf(entry to response)
        val repo = WeatherRepositoryImpl(mockApiHelper, cache)
        val value = runBlocking<WeatherByLocationResponse> {
            repo.getWeatherByLocation(entry)
        }

        Assert.assertEquals(value, response)
        Assert.assertEquals(value.current, weatherUnit)
    }
}
