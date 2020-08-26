package io.betterapps.graysky.data.models

import org.junit.Assert.assertEquals
import org.junit.Test

class WeatherUnitTest {

    val weatherUnit = WeatherUnit(1598454000, 18.13f, 12.59f, 72, 75, emptyList())

    @Test
    fun actualLocalTimeTest() {
        assertEquals("17:00", weatherUnit.actualLocalTime())
    }

    @Test
    fun offsetLocalTimeTest() {
        // timezone: "Europe/Berlin", time offset: 7200
        // timeOffset is in seconds
        assertEquals("19:00", weatherUnit.actualLocalTime(timeOffset = 7200))
    }

    @Test
    fun localTimeformattingTest() {
        assertEquals("17:00:00", weatherUnit.actualLocalTime(format = "HH:mm:ss"))
        assertEquals("05:00:00", weatherUnit.actualLocalTime(format = "hh:mm:ss"))
        assertEquals(
            "2020-08-26T19:00:00",
            weatherUnit.actualLocalTime(timeOffset = 7200, format = "yyyy-MM-dd'T'HH:mm:ss")
        )
    }

    @Test
    fun mockNewYorkTest() {
        val newYorkWeather = WeatherUnit(1598464458, 25f, 21.61f, 72, 75, emptyList())
        assertEquals(
            "2020-08-26T15:54:18",
            newYorkWeather.actualLocalTime(timeOffset = -14400, format = "yyyy-MM-dd'T'HH:mm:ss")
        )
        assertEquals(
            "15:54",
            newYorkWeather.actualLocalTime(timeOffset = -14400)
        )
    }

    @Test
    fun timezoneNewYorkTest() {
        val newYorkWeather = WeatherUnit(1598464863, 22.59f, 23.66f, 72, 75, emptyList())
        val timeOffset = -25200L
        assertEquals(
            "2020-08-26T13:01:03",
            newYorkWeather.actualLocalTime(
                timeOffset = timeOffset,
                format = "yyyy-MM-dd'T'HH:mm:ss"
            )
        )
        assertEquals(
            "13:01",
            newYorkWeather.actualLocalTime(timeOffset = timeOffset)
        )
    }
}
