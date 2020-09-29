package io.betterapps.graysky.db.entities

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.betterapps.graysky.data.db.entities.LocationDao
import io.betterapps.graysky.data.db.entities.LocationDatabase
import io.betterapps.graysky.data.db.entities.LocationEntity
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

// https://developer.android.com/training/data-storage/room/testing-db
@RunWith(AndroidJUnit4::class)
class LocationEntityReadWriteTest {

    private lateinit var dao: LocationDao
    private lateinit var db: LocationDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = LocationDatabase.getDatabase(context, "db_test")
        dao = db.locationDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.clearAllTables()
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeLocationsAndReadInList() {

        // https://stackoverflow.com/questions/49865054/how-to-unit-test-kotlin-suspending-functions
        val entity1: LocationEntity = LocationEntity("id", "Amsterdam", 52.370216, 4.895168)
        runBlocking {
            dao.insert(entity1)
        }

        val list = dao.getLocations()
        assertThat(list.get(0), equalTo(entity1))

        val entity2: LocationEntity =
            LocationEntity("id2", "Paris", 48.8534, 2.3488)
        runBlocking {
            dao.insert(entity2)
        }

        val list2 = dao.getLocations()
        assertThat(list2.size, equalTo(2))
        assertThat(list2.get(1), equalTo(entity2))
    }

    @Test
    @Throws(Exception::class)
    fun writeLocationsWithSameIdAndReadInList() {

        val uniqueID = "whatever"
        val entity1: LocationEntity = LocationEntity(uniqueID, "Amsterdam", 52.370216, 4.895168)
        runBlocking {
            dao.insert(entity1)
        }

        val list = dao.getLocations()
        assertThat(list.get(0), equalTo(entity1))

        val entity2: LocationEntity =
            LocationEntity(uniqueID, "Paris", 48.8534, 2.3488)
        // TODO handle correctly
        runBlocking {
            dao.insert(entity2)
        }

        val list2 = dao.getLocations()
        assertThat(list2.size, equalTo(1))
        assertThat(list2.get(0), equalTo(entity1))
    }
}