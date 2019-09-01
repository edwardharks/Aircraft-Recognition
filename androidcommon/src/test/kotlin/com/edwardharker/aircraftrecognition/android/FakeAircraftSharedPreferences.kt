package com.edwardharker.aircraftrecognition.android

import com.edwardharker.aircraftrecognition.android.preferences.AircraftSharedPreferences

class FakeAircraftSharedPreferences(
    private val getIntReturns: Pair<String, Int>? = null,
    private val containsReturns: Pair<String, Boolean>? = null
) : AircraftSharedPreferences {
    private val _saveIntCalls = mutableListOf<Pair<String, Int>>()
    val saveIntCalls: List<Pair<String, Int>> = _saveIntCalls

    override fun getInt(key: String, default: Int): Int {
        return if (getIntReturns?.first == key) getIntReturns.second else 0
    }

    override fun saveInt(key: String, value: Int) {
        _saveIntCalls.add(key to value)
    }

    override fun contains(key: String): Boolean {
        return if (containsReturns?.first == key) containsReturns.second else false
    }
}
