package com.example.androidfundementalsection2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CuboidEntity::class], version = 1)
abstract class CuboidRoomDatabase: RoomDatabase() {
    abstract fun getCuboidDao(): CuboidDao
    companion object{
        @Volatile
        private var INSTANCE: CuboidRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): CuboidRoomDatabase{
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context.applicationContext, CuboidRoomDatabase::class.java, "cuboid_database").build()
            }
            return INSTANCE as CuboidRoomDatabase
        }
    }
}