package com.example.androidfundementalsection2.database

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.Executors

class CuboidRepository(application: Application) {
    private lateinit var cuboidDao: CuboidDao
    private val executorService = Executors.newSingleThreadExecutor()
    init {
        val db = CuboidRoomDatabase.getDatabase(application)
        cuboidDao = db.getCuboidDao()
    }
    fun getAllCuboid(): LiveData<List<CuboidEntity>> = cuboidDao.getAllCuboid()
    fun insertCuboid(cuboidEntity: CuboidEntity) {
        executorService.execute{
            cuboidDao.insert(cuboidEntity)
        }
    }
}