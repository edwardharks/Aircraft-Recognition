package com.edwardharker.aircraftrecognition.aircraftdetail

import com.edwardharker.aircraftrecognition.model.Aircraft
import com.edwardharker.aircraftrecognition.model.Image
import org.junit.Test
import org.mockito.Mockito
import rx.Observable
import rx.schedulers.Schedulers

class PhotoCarouselPresenterTest {

    @Test
    fun showsImages() {
        val id = "id"
        val images = listOf(Image("url", 0, 0))
        val aircraft = Aircraft(images = images)
        val mockedView = Mockito.mock(PhotoCarouselView::class.java)
        val presenter = PhotoCarouselPresenter(Schedulers.immediate(), {
            if (id == it) {
                Observable.just(aircraft)
            } else {
                Observable.empty()
            }
        })
        presenter.startPresenting(mockedView, id)
        Mockito.verify(mockedView).showImages(images)
    }
}