package io.betterapps.graysky.db.entities

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.betterapps.graysky.data.db.entities.LocationDao
import io.betterapps.graysky.data.db.entities.LocationDatabase
import io.betterapps.graysky.data.db.entities.LocationEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.AfterClass
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

// https://developer.android.com/training/data-storage/room/testing-db
@RunWith(AndroidJUnit4::class)
class LocationEntityReadWriteTest {

    companion object {
        // https://stackoverflow.com/a/35554077/311420
        private lateinit var dao: LocationDao
        private lateinit var db: LocationDatabase

        @BeforeClass
        @JvmStatic
        fun setup() {
            // things to execute once and keep around for the class
            val context = ApplicationProvider.getApplicationContext<Context>()
            db = LocationDatabase.getDatabase(context, "db_test")
            dao = db.locationDao()
        }

        @AfterClass
        @Throws(IOException::class)
        @JvmStatic
        fun closeDb() {
            db.close()
        }
    }

    // before each test
    @Before
    fun resetDB() {
        db.clearAllTables()
    }

    @Test
    @Throws(Exception::class)
    fun writeLocationsAndReadInList() {

        // https://stackoverflow.com/questions/49865054/how-to-unit-test-kotlin-suspending-functions
        val entity1: LocationEntity =
            LocationEntity("id", "Amsterdam", "Amsterdam, NL", 52.370216, 4.895168)

        val entity2: LocationEntity =
            LocationEntity("id2", "Paris", "Paris, France", 48.8534, 2.3488)

        runBlocking {
            dao.insert(entity1)
            val list = dao.getLocations()
            assertThat(list.get(0), equalTo(entity1))
        }

        runBlocking {
            dao.insert(entity2)
            val list2 = dao.getLocations()
            assertThat(list2.size, equalTo(2))
            assertThat(list2.get(1), equalTo(entity2))
        }
    }

    @Test
    @Throws(Exception::class)
    fun writeLocationsWithSameIdAndReadInList() {

        val UNIQUE_ID = "whatever"
        val entity1: LocationEntity =
            LocationEntity(UNIQUE_ID, "Amsterdam", "Amsterdam, NL", 52.370216, 4.895168)
        GlobalScope.launch {
            dao.insert(entity1)

            val list = dao.getLocations()
            assertThat(list.get(0), equalTo(entity1))

            // entity2 should not be inserted due to the Conflict
            val entity2: LocationEntity =
                LocationEntity(UNIQUE_ID, "Paris", "Paris, France", 48.8534, 2.3488)


            dao.insert(entity2)

            val list2 = dao.getLocations()
            assertThat(list2.size, equalTo(1))
            assertThat(list2.get(0), equalTo(entity1))
        }
    }

    @Test
    @Throws(Exception::class)
    fun writeThenDeleteCity() {
        val NAME = "Amsterdam"
        val entity1: LocationEntity =
            LocationEntity("id", NAME, "Amsterdam, NL", 52.370216, 4.895168)
        GlobalScope.launch {
            dao.insert(entity1)
            assertEquals(1, dao.getLocations().size)

            dao.deleteCity(NAME)
            assertEquals(0, dao.getLocations().size)
        }
    }

    @Test
    @Throws(Exception::class)
    fun writeThenDeleteEntity() {
        val entity1: LocationEntity =
            LocationEntity("id", "Amsterdam", "Amsterdam, NL", 52.370216, 4.895168)

        GlobalScope.launch {
            dao.insert(entity1)
            assertEquals(1, dao.getLocations().size)

            dao.delete(entity1)
            assertEquals(0, dao.getLocations().size)
        }
    }
}