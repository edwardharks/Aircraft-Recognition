package com.edwardharker.aircraftrecognition.search

sealed class SearchEvent

data class SearchSubmitEvent(val query: String) : SearchEvent()