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
        db = LocationDatabase.getDatabase(context)
        dao = db.locationDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeLocationAndReadInList() {

        // https://stackoverflow.com/questions/49865054/how-to-unit-test-kotlin-suspending-functions
        val entity: LocationEntity = LocationEntity(1, "id", "Amsterdam", 52.370216, 4.895168)
        runBlocking {
            dao.insert(entity)
        }

        val list = dao.getLocations()
        assertThat(list.get(0), equalTo(entity))
    }
}