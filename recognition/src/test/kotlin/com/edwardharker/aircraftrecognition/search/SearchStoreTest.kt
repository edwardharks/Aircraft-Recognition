package com.edwardharker.aircraftrecognition.search

import org.junit.Test
import org.mockito.Mockito
import rx.Observable
import rx.observers.TestSubscriber

class SearchStoreTest {

    private val queryChangedAction = QueryChangedAction("query")

    private val testSubscriber = TestSubscriber<SearchState>()
    private val actionSubject = rx.subjects.PublishSubject.create<SearchAction>()

    private val noOpReducer = fun (oldState: SearchState, _: SearchAction): SearchState = oldState
    private val noOpSearchUseCase = fun (_: QueryChangedAction): Observable<SearchAction> = Observable.never()

    @Test
    fun usesSearchFunctionForQueryChangedActions() {
        val mockedSearchUseCase = Mockito.mock(MockSearchUseCase::class.java)

        SearchStore(mockedSearchUseCase::search, noOpReducer)
                .observe(actionSubject)
                .subscribe(testSubscriber)

        actionSubject.onNext(queryChangedAction)

        Mockito.verify(mockedSearchUseCase).search(queryChangedAction)
    }

    @Test
    fun startsWithEmptyState() {
        val expected = SearchState.empty()

        SearchStore(noOpSearchUseCase, noOpReducer)
                .observe(actionSubject)
                .subscribe(testSubscriber)

        actionSubject.onNext(SearchResultsAction(emptyList()))
        testSubscriber.assertValues(expected)
    }

    @Test
    fun returnsOutputOfReducer() {
        val expected = SearchState.error()
        val fakeReducer = fun (_: SearchState, _: SearchAction): SearchState = expected
        val fakeSearchUseCase = fun (_: QueryChangedAction): Observable<SearchAction> =
                Observable.just(SearchResultsAction(emptyList()))

        SearchStore(fakeSearchUseCase, fakeReducer)
                .observe(actionSubject)
                .subscribe(testSubscriber)

        actionSubject.onNext(queryChangedAction)
        testSubscriber.assertValues(SearchState.empty(), expected)
    }

    private interface MockSearchUseCase {
        fun search(action: QueryChangedAction): Observable<SearchAction>
    }
}