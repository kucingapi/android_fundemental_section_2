package com.example.androidfundementalsection2

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log

internal class CuboidPreference(context: Context) {
    companion object {
        private const val PREFS_NAME = "cuboid_pref"
        private const val WIDTH = "width"
        private const val LENGTH = "length"
        private const val HEIGHT = "height"
    }
    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    @SuppressLint("CommitPrefEdits")
    fun setCuboid(cuboidModel: CuboidModel){
        Log.d("set cuboid", "setCuboid: ")
        val editor = preferences.edit()
        editor.run {
            putString(WIDTH, cuboidModel.width.toString())
            putString(LENGTH, cuboidModel.length.toString())
            putString(HEIGHT, cuboidModel.height.toString())
            apply()
        }
    }

    fun getCuboid(): CuboidModel {
        val model = CuboidModel()
        model.length = preferences.getString(LENGTH, "")?.toDouble() ?: 0.00
        model.width = preferences.getString(WIDTH, "")?.toDouble() ?: 0.00
        model.height = preferences.getString(HEIGHT, "")?.toDouble() ?: 0.00
        return model
    }
}