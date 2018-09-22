package com.edwardharker.aircraftrecognition.search

import com.edwardharker.aircraftrecognition.model.Aircraft
import com.edwardharker.aircraftrecognition.model.displayName

object AircraftSearch {
    fun search(query: String, aircraft: List<Aircraft>): List<Aircraft> {
        if (query.isBlank()) {
            return emptyList()
        }

        return aircraft.filter {
            val queryLowerCase = query.toLowerCase()
            it.name.toLowerCase().contains(queryLowerCase) ||
                    it.manufacturer.toLowerCase().startsWith(queryLowerCase) ||
                    it.displayName.toLowerCase().startsWith(queryLowerCase)
        }
    }
}
