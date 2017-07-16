package com.edwardharker.aircraftrecognition.search

import com.edwardharker.aircraftrecognition.repository.AircraftRepository
import rx.Observable

class SearchUseCase(private val aircraftRepository: AircraftRepository) {

    fun searchAircraftByName(action: QueryChangedAction): Observable<SearchAction> =
            aircraftRepository.searchByAircraftName(action.query)
                    .map { SearchResultsAction(it) }
}
