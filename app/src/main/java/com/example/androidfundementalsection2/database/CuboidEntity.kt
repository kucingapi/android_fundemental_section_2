package com.example.androidfundementalsection2.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class CuboidEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "width")
    var width: Double? = 0.00,
    @ColumnInfo(name = "length")
    var length: Double? = 0.00,
    @ColumnInfo(name = "height")
    var height: Double? = 0.00,
): Parcelable
