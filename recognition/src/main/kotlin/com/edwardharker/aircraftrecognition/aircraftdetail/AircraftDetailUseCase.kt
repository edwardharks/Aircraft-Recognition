package com.edwardharker.aircraftrecognition.aircraftdetail

import com.edwardharker.aircraftrecognition.model.Aircraft
import rx.Observable

interface AircraftDetailUseCase {

    fun aircraft(id: String): Observable<Aircraft>
}
