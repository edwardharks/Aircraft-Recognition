package com.edwardharker.aircraftrecognition.search

import com.edwardharker.aircraftrecognition.model.Aircraft

sealed class SearchAction

data class QueryChangedAction(val query: String) : SearchAction()

data class SearchResultsAction(val aircraft: List<Aircraft>) : SearchAction()
