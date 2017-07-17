package com.edwardharker.aircraftrecognition.search

import rx.Observable
import rx.Subscription
import rx.subjects.PublishSubject


class SearchStore(private val search: (action: QueryChangedAction) -> Observable<SearchAction>,
                  reducer: (oldState: SearchState, action: SearchAction) -> SearchState) {

    private val actions = PublishSubject.create<SearchAction>()

    private val states = actions.compose {
        actions.publish {
            it.ofType(QueryChangedAction::class.java).compose(this::queryChangedAction)
        }
    }.scan(SearchState.empty(), reducer)

    fun dispatch(action: SearchAction) = actions.onNext(action)

    fun dispatch(actions: Observable<SearchAction>): Subscription = actions.subscribe { dispatch(it) }

    fun subscribe(): Observable<SearchState> = states

    private fun queryChangedAction(actions: Observable<QueryChangedAction>) = actions.flatMap(search)

}
