package com.edwardharker.aircraftrecognition.search

import redux.Action

object SearchReducer {

    fun reduce(oldState: SearchState, action: Action): SearchState {
        when (action) {
            is SearchResultsAction -> return SearchState.success(action.aircraft)
            else -> return oldState
        }
    }
}
