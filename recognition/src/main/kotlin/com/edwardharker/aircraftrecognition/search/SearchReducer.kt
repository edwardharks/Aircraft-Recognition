package com.edwardharker.aircraftrecognition.search

import redux.Action
import redux.Reducer
import redux.State

object SearchReducer : Reducer {

    override fun reduce(oldState: State, action: Action): State {
        when (action) {
            is SearchResultsAction -> return SearchState.success(action.aircraft)
            else -> return oldState
        }
    }
}
