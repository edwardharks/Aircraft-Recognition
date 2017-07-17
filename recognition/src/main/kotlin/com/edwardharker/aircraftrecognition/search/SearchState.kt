package com.edwardharker.aircraftrecognition.search

import com.edwardharker.aircraftrecognition.model.Aircraft
import redux.State

data class SearchState(val searchResults: List<Aircraft> = emptyList(),
                       val error: Boolean = false) : State() {

    companion object {

        fun empty() = SearchState()

        fun success(aircraft: List<Aircraft>) = SearchState(searchResults = aircraft)

        fun error() = SearchState(error = true)
    }
}
