package com.edwardharker.aircraftrecognition.similaraircraft

import com.edwardharker.aircraftrecognition.model.Aircraft
import org.junit.Test
import org.mockito.Mockito.*
import rx.Observable
import rx.schedulers.Schedulers

class SimilarAircraftPresenterTest {

    private val id = "id"
    private val aircraft = Aircraft()
    private val mockedView = mock(SimilarAircraftMvpView::class.java)

    @Test
    fun `shows aircraft`() {
        val presenter = SimilarAircraftPresenter(Schedulers.immediate()) { id ->
            if (this.id == id) {
                Observable.just(listOf(aircraft))
            } else {
                Observable.empty()
            }
        }

        presenter.startPresenting(mockedView, id)
        verify(mockedView).showSimilarAircraft(listOf(aircraft))
    }

    @Test
    fun `hides view when no similar aircraft`() {
        val presenter = SimilarAircraftPresenter(Schedulers.immediate()) { Observable.just(listOf()) }
        presenter.startPresenting(mockedView, id)
        verify(mockedView).hideView()
    }
}
