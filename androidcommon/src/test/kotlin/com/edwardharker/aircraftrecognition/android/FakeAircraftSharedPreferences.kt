package com.edwardharker.aircraftrecognition.android

import com.edwardharker.aircraftrecognition.android.preferences.AircraftSharedPreferences

class FakeAircraftSharedPreferences(
    private val thatReturns: Pair<String, Int>
) : AircraftSharedPreferences {
    override fun getInt(key: String, default: Int): Int {
        return if (thatReturns.first == key) thatReturns.second else 0
    }

    override fun saveInt(key: String, value: Int) {}
}
