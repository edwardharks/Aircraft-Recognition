package com.edwardharker.aircraftrecognition.search

import rx.Observable


class SearchStore(private val search: (action: QueryChangedAction) -> Observable<SearchAction>,
                  private val reducer: (oldState: SearchState, action: SearchAction) -> SearchState) {

    fun observe(actions: Observable<SearchAction>): Observable<SearchState> =
            actions.compose {
                actions.publish {
                    it.ofType(QueryChangedAction::class.java).compose(this::queryChangedAction)
                }
            }.scan(SearchState.empty(), reducer)


    private fun queryChangedAction(actions: Observable<QueryChangedAction>) = actions.flatMap(search)

}
