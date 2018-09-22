package com.edwardharker.aircraftrecognition.search

import org.junit.Test
import org.mockito.Mockito
import rx.Observable
import rx.observers.TestSubscriber

class SearchStoreTest {
    private val testSubscriber = TestSubscriber<SearchState>()
    private val actionSubject = rx.subjects.PublishSubject.create<SearchAction>()

    @Test
    fun usesSearchFunctionForQueryChangedActions() {
        val mockedSearchUseCase = Mockito.mock(MockSearchUseCase::class.java)

        val store = SearchStore(mockedSearchUseCase::search, noOpReducer)
        store.dispatch(actionSubject)
        store.subscribe().subscribe(testSubscriber)

        actionSubject.onNext(queryChangedAction)

        Mockito.verify(mockedSearchUseCase).search(queryChangedAction)
    }

    @Test
    fun startsWithEmptyState() {
        val expected = SearchState.empty()

        val store = SearchStore(noOpSearchUseCase, noOpReducer)
        store.dispatch(actionSubject)
        store.subscribe().subscribe(testSubscriber)

        actionSubject.onNext(SearchResultsAction(emptyList()))
        testSubscriber.assertValues(expected)
    }

    @Test
    fun returnsOutputOfReducer() {
        val expected = SearchState.error()
        val fakeReducer = fun(_: SearchState, _: SearchAction): SearchState = expected
        val fakeSearchUseCase = fun(_: QueryChangedAction): Observable<SearchAction> =
            Observable.just(SearchResultsAction(emptyList()))

        val store = SearchStore(fakeSearchUseCase, fakeReducer)
        store.dispatch(actionSubject)
        store.subscribe().subscribe(testSubscriber)

        actionSubject.onNext(queryChangedAction)
        testSubscriber.assertValues(SearchState.empty(), expected)
    }

    private interface MockSearchUseCase {
        fun search(action: QueryChangedAction): Observable<SearchAction>
    }

    private companion object {
        private val queryChangedAction = QueryChangedAction("query")

        private val noOpReducer =
            fun(oldState: SearchState, _: SearchAction): SearchState {
                return oldState
            }

        private val noOpSearchUseCase =
            fun(_: QueryChangedAction): Observable<SearchAction> {
                return Observable.never()
            }
    }
}