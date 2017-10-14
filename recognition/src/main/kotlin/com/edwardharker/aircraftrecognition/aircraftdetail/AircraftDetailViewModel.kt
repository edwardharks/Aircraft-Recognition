package com.edwardharker.aircraftrecognition.aircraftdetail

import com.edwardharker.aircraftrecognition.model.Aircraft

data class AircraftDetailViewModel(
        val aircraft: Aircraft,
        val featuredVideoId: String?
)
