package io.betterapps.graysky.utils

import io.betterapps.graysky.data.models.GeoLocation
import org.junit.Assert
import org.junit.Test

class DistanceTest {

    @Test
    fun distanceParisBerlinTest() {
        val Berlin = GeoLocation(52.520007, 13.404954)
        val Paris = GeoLocation(48.8534, 2.3488)

        val distanceBerlinParis = DistanceUtils.distance(Berlin, Paris)
        val distanceParisBerlin = DistanceUtils.distance(Paris, Berlin)


        Assert.assertEquals(distanceBerlinParis, distanceParisBerlin, 0.001)
        Assert.assertEquals(877.859, distanceParisBerlin, 0.001)
    }

    @Test
    fun distanceSantaClaraParisTest() {
        val SantaClara = GeoLocation(37.3997, -121.9608)
        val Paris = GeoLocation(48.8534, 2.3488)

        val distanceParisSantaClara = DistanceUtils.distance(Paris, SantaClara)

        Assert.assertEquals(8966.1529, distanceParisSantaClara, 0.001)
    }

    @Test
    fun distanceTahitiParisTest() {
        val Tahiti = GeoLocation(-17.650920, -149.426042)
        val Paris = GeoLocation(48.8534, 2.3488)

        val distanceParisTahiti = DistanceUtils.distance(Paris, Tahiti)
        Assert.assertEquals(15715.484, distanceParisTahiti, 0.001)
        // extension function
        Assert.assertEquals(15715.484, Tahiti.distance(Paris), 0.001)
    }
}