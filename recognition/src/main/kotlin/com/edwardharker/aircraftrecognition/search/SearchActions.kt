package com.edwardharker.aircraftrecognition.search

import com.edwardharker.aircraftrecognition.model.Aircraft
import redux.Action

sealed class SearchAction: Action

data class QueryChangedAction(val query: String) : SearchAction()

data class SearchResultsAction(val aircraft: List<Aircraft>) : SearchAction()
