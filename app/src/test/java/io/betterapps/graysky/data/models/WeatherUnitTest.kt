package io.betterapps.graysky.data.models

import org.junit.Assert.assertEquals
import org.junit.Test

class WeatherUnitTest {

    val weatherUnit = WeatherUnit(1598454000, 18.13f, 12.59f, 72, 75, emptyList())

    @Test
    fun actualLocalTimeTest() {
        assertEquals("15:00", weatherUnit.actualLocalTime())
    }

    @Test
    fun offsetLocalTimeTest() {
        // timezone: "Europe/Berlin", time offset: 7200
        // timeOffset is in seconds
        assertEquals("17:00", weatherUnit.actualLocalTime(timeOffset = 7200))
    }

    @Test
    fun localTimeformattingTest() {
        assertEquals("15:00:00", weatherUnit.actualLocalTime(format = "HH:mm:ss"))
        assertEquals("03:00:00", weatherUnit.actualLocalTime(format = "hh:mm:ss"))
        assertEquals(
            "2020-08-26T17:00:00",
            weatherUnit.actualLocalTime(timeOffset = 7200, format = "yyyy-MM-dd'T'HH:mm:ss")
        )
    }
}
