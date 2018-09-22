package com.edwardharker.aircraftrecognition.filter.results

import com.edwardharker.aircraftrecognition.model.Aircraft
import rx.Observable

interface FilterResultsUseCase {
    fun filteredAircraft(): Observable<List<Aircraft>>
}
