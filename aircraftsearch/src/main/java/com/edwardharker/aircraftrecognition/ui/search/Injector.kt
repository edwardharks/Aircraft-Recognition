package com.edwardharker.aircraftrecognition.ui.search

import com.edwardharker.aircraftrecognition.repository.aircraftRepository
import com.edwardharker.aircraftrecognition.search.AircraftSearch
import com.edwardharker.aircraftrecognition.search.QueryChangedAction
import com.edwardharker.aircraftrecognition.search.SearchReducer
import com.edwardharker.aircraftrecognition.search.SearchUseCase
import redux.Action
import redux.Store
import rx.Observable

private fun searchUseCase(): SearchUseCase = SearchUseCase(aircraftRepository(), AircraftSearch::search)

fun searchUseCaseAction(actions: Observable<Action>): Observable<Action> =
        actions.ofType(QueryChangedAction::class.java).compose(searchUseCase()::searchAircraftByName)

fun searchStore(): Store = Store(SearchReducer, ::searchUseCaseAction)
