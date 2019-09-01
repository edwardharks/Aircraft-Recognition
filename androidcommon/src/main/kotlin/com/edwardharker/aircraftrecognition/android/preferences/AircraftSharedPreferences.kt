package com.edwardharker.aircraftrecognition.android.preferences

interface AircraftSharedPreferences {
    fun getInt(key: String, default: Int): Int

    fun saveInt(key: String, value: Int)

    fun contains(key: String): Boolean
}
