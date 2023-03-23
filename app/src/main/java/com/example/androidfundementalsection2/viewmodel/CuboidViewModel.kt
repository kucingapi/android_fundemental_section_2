package com.example.androidfundementalsection2.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.androidfundementalsection2.database.CuboidEntity
import com.example.androidfundementalsection2.database.CuboidRepository

class CuboidViewModel(application: Application): ViewModel() {
    private val repository: CuboidRepository = CuboidRepository(application)

    fun getAllCuboid(): LiveData<List<CuboidEntity>> = repository.getAllCuboid()
    fun insert(cuboidEntity: CuboidEntity){
        repository.insertCuboid(cuboidEntity)
    }
}