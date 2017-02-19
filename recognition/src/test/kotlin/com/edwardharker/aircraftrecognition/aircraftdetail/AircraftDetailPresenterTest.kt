package com.edwardharker.aircraftrecognition.aircraftdetail

import com.edwardharker.aircraftrecognition.model.Aircraft
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify
import rx.Observable
import rx.schedulers.Schedulers.immediate

class AircraftDetailPresenterTest {

    @Test
    fun showsAircraft() {
        val id = "id"
        val aircraft = Aircraft()
        val mockedView = Mockito.mock(AircraftDetailView::class.java)
        val presenter = AircraftDetailPresenter(immediate(), {
            if (id == it) {
                Observable.just(aircraft)
            } else {
                Observable.empty()
            }
        })
        presenter.startPresenting(mockedView, id)
        verify(mockedView).showAircraft(aircraft)
    }
}