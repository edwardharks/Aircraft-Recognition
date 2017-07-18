package com.edwardharker.aircraftrecognition.search

import redux.Action
import redux.Reducer

object SearchReducer : Reducer<SearchState> {

    override fun reduce(oldState: SearchState, action: Action): SearchState {
        when (action) {
            is SearchResultsAction -> return SearchState.success(action.aircraft)
            else -> return oldState
        }
    }
}
