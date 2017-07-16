package com.edwardharker.aircraftrecognition.search

import com.edwardharker.aircraftrecognition.model.Aircraft

object AircraftSearch {

    fun search(query: String, aircraft: List<Aircraft>): List<Aircraft> {
        if (query.isNullOrBlank()) {
            return emptyList()
        }

        return aircraft.filter {
            val queryLowerCase = query.toLowerCase()
            it.name.toLowerCase().contains(queryLowerCase) ||
                    it.manufacturer.toLowerCase().startsWith(queryLowerCase)
        }
    }
}
