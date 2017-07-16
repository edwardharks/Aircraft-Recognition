package com.edwardharker.aircraftrecognition.search

import rx.Observable


class SearchStore(private val search: (action: QueryChangedAction) -> Observable<SearchAction>,
                  private val reducer: (oldState: SearchState, action: SearchAction) -> SearchState) {

    fun observe(actions: Observable<SearchAction>): Observable<SearchState> =
            actions.compose(this::actions)
                    .scan(SearchState.empty(), reducer)

    private fun actions(actions: Observable<SearchAction>): Observable<SearchAction> =
            // if there were more actions, use merge here
            actions.publish { it.ofType(QueryChangedAction::class.java).compose(this::searchQuery) }

    private fun searchQuery(actions: Observable<QueryChangedAction>) = actions.flatMap(search)

}
