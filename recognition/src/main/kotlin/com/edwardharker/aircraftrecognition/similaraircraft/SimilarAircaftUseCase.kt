package com.edwardharker.aircraftrecognition.similaraircraft

import com.edwardharker.aircraftrecognition.model.Aircraft
import com.edwardharker.aircraftrecognition.repository.AircraftRepository
import rx.Observable

class SimilarAircaftUseCase(private val aircraftRepository: AircraftRepository) :
        (String) -> Observable<List<Aircraft>> {
    override fun invoke(aircraftId: String): Observable<List<Aircraft>> =
        aircraftRepository.findAircraftById(aircraftId)
            .flatMap { aircraft ->
                aircraftRepository.filteredAircraft(aircraft.filterOptions)
            }
            .map { aircrafts ->
                aircrafts.filter { it.id != aircraftId }
            }
}
