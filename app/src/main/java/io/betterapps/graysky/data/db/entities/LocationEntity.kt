package io.betterapps.graysky.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_table")
data class LocationEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "insert_id") val insertOrderID: Int,
    @ColumnInfo(name = "id") val referenceID: String, //ChIJOwg_06VPwokRYv534QaPC8g
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double
)