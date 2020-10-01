package io.betterapps.graysky.repository

import com.nhaarman.mockitokotlin2.mock
import io.betterapps.graysky.data.db.entities.LocationDao
import io.betterapps.graysky.data.db.entities.LocationEntity
import io.betterapps.graysky.utils.toLocationName
import org.junit.Assert
import org.junit.Test

class LocationRepositoryTest {

    @Test
    fun addLocationTest() {
        val dao = mock<LocationDao>()
        val repository = LocationRepositoryImpl(dao)

        val entity1 = LocationEntity("id", "Amsterdam", "Amsterdam, NL", 52.370216, 4.895168)
        repository.addLocation(entity1)

        Assert.assertEquals(1, repository.cache.size)

        val entity2: LocationEntity =
            LocationEntity("id2", "Paris", "Paris, France", 48.8534, 2.3488)
        repository.addLocation(entity2)

        Assert.assertEquals(2, repository.cache.size)
        Assert.assertEquals(entity1.toLocationName(), repository.cache[0])
        Assert.assertEquals(entity2.toLocationName(), repository.cache[1])
    }

    @Test
    fun deleteLocationTest() {
        val dao = mock<LocationDao>()
        val repository = LocationRepositoryImpl(dao)

        Assert.assertEquals(0, repository.cache.size)

        val entity1 = LocationEntity("id", "Amsterdam", "Amsterdam, NL", 52.370216, 4.895168)
        repository.addLocation(entity1)
        Assert.assertEquals(1, repository.cache.size)

        repository.deleteLocation("Wrong Name")
        Assert.assertEquals(1, repository.cache.size)

        repository.deleteLocation("Amsterdam")
        Assert.assertEquals(0, repository.cache.size)
    }
}