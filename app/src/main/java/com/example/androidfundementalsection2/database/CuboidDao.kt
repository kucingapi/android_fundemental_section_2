package com.example.androidfundementalsection2.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CuboidDao {
    @Insert
    fun insert(cuboidEntity: CuboidEntity)

    @Query("SELECT * FROM cuboidentity")
    fun getAllCuboid(): LiveData<List<CuboidEntity>>
}