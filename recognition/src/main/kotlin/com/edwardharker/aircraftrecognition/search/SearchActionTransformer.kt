package com.edwardharker.aircraftrecognition.search

import redux.Action
import redux.ActionTransformer
import rx.Observable

class SearchActionTransformer(private val searchUseCase: SearchUseCase) : ActionTransformer {

    override fun transform(actions: Observable<Action>): Observable<Action> =
            actions.ofType(QueryChangedAction::class.java).compose(searchUseCase::searchAircraftByName)
}
