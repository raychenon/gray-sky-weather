package io.betterapps.graysky.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "location_table",
    // indices = {@Index(value = {"latitude", "longitude"}, unique = true)}
    indices = [Index(value = ["latitude", "longitude"], unique = true)]
)
data class LocationEntity(
    @PrimaryKey @ColumnInfo(name = "id") val referenceID: String, //ChIJOwg_06VPwokRYv534QaPC8g
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double
)