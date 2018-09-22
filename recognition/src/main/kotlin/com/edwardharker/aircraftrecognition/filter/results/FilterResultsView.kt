package com.edwardharker.aircraftrecognition.filter.results

import com.edwardharker.aircraftrecognition.model.Aircraft

interface FilterResultsView {
    fun showAircraft(aircraft: List<Aircraft>)
}
