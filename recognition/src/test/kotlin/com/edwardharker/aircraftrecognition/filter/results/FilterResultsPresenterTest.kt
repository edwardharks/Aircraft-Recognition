package com.edwardharker.aircraftrecognition.filter.results

import com.edwardharker.aircraftrecognition.model.Aircraft
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import rx.Observable
import rx.schedulers.Schedulers.immediate

class FilterResultsPresenterTest {
    @Test
    fun showsAircraft() {
        val aircraft = listOf(Aircraft())
        val mockedView = mock(FilterResultsView::class.java)

        val presenter = FilterResultsPresenter(
            mainScheduler = immediate(),
            filterResultsUseCase = { Observable.just(aircraft) }
        )

        presenter.startPresenting(mockedView)
        verify(mockedView).showAircraft(aircraft)
    }
}
