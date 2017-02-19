package com.edwardharker.aircraftrecognition.aircraftdetail

import com.edwardharker.aircraftrecognition.model.Aircraft
import com.edwardharker.aircraftrecognition.repository.AircraftRepository
import rx.Observable

class RepositoryAircraftDetailUseCase(private val aircraftRepository: AircraftRepository) : AircraftDetailUseCase {

    override fun aircraft(id: String): Observable<Aircraft> = aircraftRepository.findAircraftById(id)
}