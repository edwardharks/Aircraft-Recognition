package com.edwardharker.aircraftrecognition.ui.search

import com.edwardharker.aircraftrecognition.repository.aircraftRepository
import com.edwardharker.aircraftrecognition.search.AircraftSearch
import com.edwardharker.aircraftrecognition.search.SearchReducer
import com.edwardharker.aircraftrecognition.search.SearchStore
import com.edwardharker.aircraftrecognition.search.SearchUseCase

private fun searchUseCase(): SearchUseCase = SearchUseCase(aircraftRepository(), AircraftSearch::search)

fun searchStore(): SearchStore = SearchStore(searchUseCase()::searchAircraftByName, SearchReducer::reduce)
