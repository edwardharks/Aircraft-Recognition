package com.edwardharker.aircraftrecognition.ui.search

import com.edwardharker.aircraftrecognition.repository.aircraftRepository
import com.edwardharker.aircraftrecognition.search.*
import redux.Store

fun searchUseCase(): SearchUseCase = SearchUseCase(aircraftRepository(), AircraftSearch::search)

fun searchStore(): Store<SearchState> = Store(SearchState(), SearchReducer)
