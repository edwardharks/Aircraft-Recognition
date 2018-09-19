package com.edwardharker.aircraftrecognition.aircraftdetail

import com.edwardharker.aircraftrecognition.model.Aircraft
import rx.Observable

class FakeAircraftDetailUseCase(
    private val withAircraftForId: Pair<String, Aircraft>? = null
) : AircraftDetailUseCase {
    override fun getAircraft(id: String): Observable<Aircraft> {
        return if (id == withAircraftForId?.first) {
            Observable.just(withAircraftForId.second)
        } else {
            Observable.empty()
        }
    }
}