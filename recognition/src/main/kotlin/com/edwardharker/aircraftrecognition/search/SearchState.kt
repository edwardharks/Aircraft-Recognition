package com.edwardharker.aircraftrecognition.search

import com.edwardharker.aircraftrecognition.model.Aircraft

data class SearchState(val searchResults: List<Aircraft> = emptyList(),
                       val error: Boolean = false) {

    companion object {

        fun empty() = SearchState()

        fun success(aircraft: List<Aircraft>) = SearchState(searchResults = aircraft)

        fun error() = SearchState(error = true)
    }
}
