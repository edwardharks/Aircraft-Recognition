package com.edwardharker.aircraftrecognition.search

import com.edwardharker.aircraftrecognition.repository.AircraftRepository
import rx.Observable


class SearchStore(private val aircraftRepository: AircraftRepository, private val reducer: (oldState: SearchState, action: SearchResultsAction) -> SearchState) {

    fun observe(actions: Observable<SearchAction>): Observable<SearchState> =
        actions.ofType(QueryChangedAction::class.java)
                .compose(this::searchQuery)
                .scan(SearchState.empty(), reducer)

    private fun searchQuery(actions: Observable<QueryChangedAction>): Observable<SearchResultsAction> =
            actions.flatMap { aircraftRepository.searchByAircraftName(it.query) }
                    .map { SearchResultsAction(it) }

}
