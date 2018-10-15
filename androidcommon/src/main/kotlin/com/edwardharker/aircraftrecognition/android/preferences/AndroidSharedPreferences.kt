package com.edwardharker.aircraftrecognition.android.preferences

import android.annotation.SuppressLint
import android.content.SharedPreferences

class AndroidSharedPreferences(
    private val sharedPreferences: SharedPreferences
) : AircraftSharedPreferences {
    override fun getInt(key: String, default: Int): Int {
        return sharedPreferences.getInt(key, default)
    }

    @SuppressLint("CommitPrefEdits")
    override fun saveInt(key: String, value: Int) {
        sharedPreferences.edit().apply {
            putInt(key, value)
            apply()
        }
    }
}
