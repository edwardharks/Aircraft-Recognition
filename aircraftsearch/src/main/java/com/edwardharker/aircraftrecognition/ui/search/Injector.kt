package com.edwardharker.aircraftrecognition.ui.search

import com.edwardharker.aircraftrecognition.repository.aircraftRepository
import com.edwardharker.aircraftrecognition.search.*
import redux.Store

private fun searchUseCase(): SearchUseCase = SearchUseCase(aircraftRepository(), AircraftSearch::search)

private fun searchActionTransformer(): SearchActionTransformer = SearchActionTransformer(searchUseCase())

fun searchStore(): Store<SearchState> = Store(SearchState(), SearchReducer, searchActionTransformer())
