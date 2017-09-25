package com.edwardharker.aircraftrecognition.similaraircraft

import com.edwardharker.aircraftrecognition.model.Aircraft

interface SimilarAircraftMvpView {
    fun showSimilarAircraft(aircraft: List<Aircraft>)
    fun hideView()
}