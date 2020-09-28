package io.betterapps.graysky.data.db.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// https://codelabs.developers.google.com/codelabs/android-room-with-a-view-kotlin/index.html?index=..%2F..index#5
@Dao
interface LocationDao {
    @Query("SELECT * from location_table ORDER BY name ASC")
    fun getLocations(): List<LocationEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(location: LocationEntity)

    @Query("DELETE FROM location_table WHERE name = :locationName")
    suspend fun deleteCity(locationName: String)

    @Query("DELETE FROM location_table")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(entity: LocationEntity)
}