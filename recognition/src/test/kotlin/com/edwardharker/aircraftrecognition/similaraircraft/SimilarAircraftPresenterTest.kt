package com.edwardharker.aircraftrecognition.similaraircraft

import com.edwardharker.aircraftrecognition.model.Aircraft
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import rx.Observable
import rx.schedulers.Schedulers

class SimilarAircraftPresenterTest {
    private val mockedView = mock(SimilarAircraftMvpView::class.java)

    @Test
    fun `shows aircraft`() {
        val presenter = SimilarAircraftPresenter(
            mainScheduler = Schedulers.immediate(),
            similarAircraft = { id ->
                if (ID == id) {
                    Observable.just(listOf(AIRCRAFT))
                } else {
                    Observable.empty()
                }
            }
        )

        presenter.startPresenting(mockedView, ID)
        verify(mockedView).showSimilarAircraft(listOf(AIRCRAFT))
    }

    @Test
    fun `hides view when no similar aircraft`() {
        val presenter =
            SimilarAircraftPresenter(
                mainScheduler = Schedulers.immediate(),
                similarAircraft = { Observable.just(listOf()) }
            )
        presenter.startPresenting(mockedView, ID)
        verify(mockedView).hideView()
    }

    private companion object {
        private const val ID = "id"
        private val AIRCRAFT = Aircraft()
    }
}
