package com.example.androidfundementalsection2

class CuboidModel {
    var width = 0.0
    var length = 0.0
    var height = 0.0
    fun getVolume(): Double = width * length * height
    fun getSurfaceArea(): Double {
        val wl = width * length
        val wh = width * height
        val lh = length * height
        return 2 * (wl + wh + lh)
    }
    fun getCircumference(): Double = 4 * (width + length + height)
    fun save(width: Double, length: Double, height: Double) {
        this.width = width
        this.length = length
        this.height = height
    }
}