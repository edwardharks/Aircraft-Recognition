package com.edwardharker.aircraftrecognition.android

class FakeAircraftSharedPreferences(
        private vararg val thatReturns: Pair<String, Any>
) : AircraftSharedPreferences {

    override fun getInt(key: String, default: Int): Int =
            thatReturns.first { it.first == key }.second as Int

    override fun saveInt(key: String, value: Int) {}
}
