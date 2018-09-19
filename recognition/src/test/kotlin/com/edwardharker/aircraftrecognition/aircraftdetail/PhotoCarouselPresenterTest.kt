package com.edwardharker.aircraftrecognition.aircraftdetail

import com.edwardharker.aircraftrecognition.model.Aircraft
import com.edwardharker.aircraftrecognition.model.Image
import org.junit.Test
import org.mockito.Mockito.*
import rx.schedulers.Schedulers

class PhotoCarouselPresenterTest {
    @Test
    fun `shows images`() {
        val mockedView = mock(PhotoCarouselView::class.java)
        val aircraftDetailUseCase = FakeAircraftDetailUseCase(
            withAircraftForId = ID to AIRCRAFT
        )
        val presenter = PhotoCarouselPresenter(
            mainScheduler = Schedulers.immediate(),
            aircraftDetailUseCase = aircraftDetailUseCase
        )

        presenter.startPresenting(mockedView, ID)

        verify(mockedView).showImages(IMAGES)
    }

    @Test
    fun `dismisses view when aircraft id is null`() {
        val presenter = PhotoCarouselPresenter(Schedulers.immediate(), FakeAircraftDetailUseCase())
        val mockedView = mock(PhotoCarouselView::class.java)

        presenter.startPresenting(mockedView, null)

        verify(mockedView).dismiss()
    }

    private companion object {
        private const val ID = "id"
        private val IMAGES = listOf(Image("url", 0, 0))
        private val AIRCRAFT = Aircraft(images = IMAGES)
    }
}