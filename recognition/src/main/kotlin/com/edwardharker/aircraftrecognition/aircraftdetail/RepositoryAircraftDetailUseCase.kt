package com.edwardharker.aircraftrecognition.aircraftdetail

import com.edwardharker.aircraftrecognition.model.Aircraft
import com.edwardharker.aircraftrecognition.repository.AircraftRepository
import rx.Observable

class RepositoryAircraftDetailUseCase(
    private val aircraftRepository: AircraftRepository
) : AircraftDetailUseCase {
    override fun getAircraft(id: String): Observable<Aircraft> {
        return aircraftRepository.findAircraftById(id)
    }
}
