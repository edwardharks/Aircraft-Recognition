package com.edwardharker.aircraftrecognition.search

import com.edwardharker.aircraftrecognition.model.Aircraft
import java.util.Locale.ROOT

object AircraftSearch {
    fun search(query: String, aircraft: List<Aircraft>): List<Aircraft> {
        if (query.isBlank()) {
            return emptyList()
        }
        val queryLowerCase = query.toLowerCase(ROOT)
        return aircraft.filter {
            it.name.toLowerCase(ROOT).contains(queryLowerCase)
        }
    }
}
