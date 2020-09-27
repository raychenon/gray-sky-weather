package io.betterapps.graysky.utils

import org.junit.Assert
import org.junit.Test

class TimeUtilsTest {

    @Test
    fun timezoneNewYorkTest() {
        val timestamp = 1598464458L
        val timeOffset = -14400L
        Assert.assertEquals(
            "2020-08-26T13:54:18",
            TimeUtils.formatLocalTime(timestamp, "yyyy-MM-dd'T'HH:mm:ss", timeOffset = timeOffset)
        )
        Assert.assertEquals(
            "13:54",
            TimeUtils.formatLocalTime(timestamp, timeOffset = timeOffset)
        )
        Assert.assertEquals(
            13,
            TimeUtils.hourLocalTime(timestamp, timeOffset = timeOffset)
        )
    }

    @Test
    fun timezoneSantaClaraTest() {
        val timestamp = 1598464863L
        val timeOffset = -25200L
        Assert.assertEquals(
            "2020-08-26T11:01:03",
            TimeUtils.formatLocalTime(timestamp, "yyyy-MM-dd'T'HH:mm:ss", timeOffset)
        )
        Assert.assertEquals(
            "11:01",
            TimeUtils.formatLocalTime(timestamp, timeOffset)
        )
        Assert.assertEquals(
            11,
            TimeUtils.hourLocalTime(timestamp, timeOffset)
        )
        Assert.assertEquals(
            "Thu\n" +
                "27\n" +
                "Aug",
            TimeUtils.formatNextDay(timestamp, timeOffset)
        )
    }
}
