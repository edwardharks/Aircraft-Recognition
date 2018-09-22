package com.edwardharker.aircraftrecognition.ui.search

import com.edwardharker.aircraftrecognition.repository.aircraftRepository
import com.edwardharker.aircraftrecognition.search.AircraftSearch
import com.edwardharker.aircraftrecognition.search.SearchReducer
import com.edwardharker.aircraftrecognition.search.SearchState
import com.edwardharker.aircraftrecognition.search.SearchUseCase
import redux.Store

fun searchUseCase(): SearchUseCase {
    return SearchUseCase(
        aircraftRepository = aircraftRepository(),
        aircraftSearch = AircraftSearch::search
    )
}

fun searchStore(): Store<SearchState> {
    return Store(
        reducer = SearchReducer::reduce,
        initialState = SearchState()
    )
}
