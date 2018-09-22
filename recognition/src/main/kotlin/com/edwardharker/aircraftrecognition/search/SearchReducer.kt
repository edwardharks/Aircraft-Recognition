package com.edwardharker.aircraftrecognition.search

import redux.Action

object SearchReducer {
    fun reduce(oldState: SearchState, action: Action): SearchState {
        return when (action) {
            is SearchResultsAction -> SearchState.success(action.aircraft)
            else -> oldState
        }
    }
}
