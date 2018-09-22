package com.edwardharker.aircraftrecognition.filter.results

import com.edwardharker.aircraftrecognition.filter.SelectedFilterOptions
import com.edwardharker.aircraftrecognition.model.Aircraft
import com.edwardharker.aircraftrecognition.repository.AircraftRepository
import rx.Observable

class RepositoryFilterResultsUseCase(
    private val selectedFilterOptions: SelectedFilterOptions,
    private val aircraftRepository: AircraftRepository
) : FilterResultsUseCase {
    override fun filteredAircraft(): Observable<List<Aircraft>> {
        return selectedFilterOptions.asObservable()
            .flatMap { aircraftRepository.filteredAircraft(it) }
    }
}
