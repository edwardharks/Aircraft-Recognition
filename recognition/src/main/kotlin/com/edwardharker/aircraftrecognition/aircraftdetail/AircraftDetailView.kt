package com.edwardharker.aircraftrecognition.aircraftdetail

import com.edwardharker.aircraftrecognition.model.Aircraft

interface AircraftDetailView {

    fun showAircraft(aircraft: Aircraft)
}