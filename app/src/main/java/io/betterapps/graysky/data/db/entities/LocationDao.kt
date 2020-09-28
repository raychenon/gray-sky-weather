package io.betterapps.graysky.data.db.entities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LocationDao {
    @Query("SELECT * from location_table ORDER BY name ASC")
    fun getAlphabetizedWords(): List<LocationEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(location: LocationEntity)

    @Query("DELETE FROM location_table WHERE name = :locationName")
    suspend fun deleteCity(locationName: String)

    @Query("DELETE FROM location_table")
    suspend fun deleteAll()
}