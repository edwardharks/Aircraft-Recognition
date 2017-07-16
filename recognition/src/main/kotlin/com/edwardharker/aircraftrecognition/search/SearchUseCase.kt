package com.edwardharker.aircraftrecognition.search

import com.edwardharker.aircraftrecognition.model.Aircraft
import com.edwardharker.aircraftrecognition.repository.AircraftRepository
import rx.Observable

class SearchUseCase(private val aircraftRepository: AircraftRepository, private val aircraftSearch: (query: String, List<Aircraft>) -> List<Aircraft>) {

    private val aircraftObservable by lazy { aircraftRepository.allAircraft().cache() }

    fun searchAircraftByName(action: QueryChangedAction): Observable<SearchAction> =
            aircraftObservable
                    .map { aircraftSearch(action.query, it) }
                    .map { SearchResultsAction(it) }
}
