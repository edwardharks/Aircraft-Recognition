package com.edwardharker.aircraftrecognition.search

object SearchReducer {

    fun reduce(oldState: SearchState, action: SearchAction): SearchState {
        when (action) {
            is SearchResultsAction -> return SearchState.success(action.aircraft)
            else -> return oldState
        }
    }
}
