package com.edwardharker.aircraftrecognition.aircraftdetail

import com.edwardharker.aircraftrecognition.model.Aircraft
import com.edwardharker.aircraftrecognition.model.YoutubeVideo
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify
import rx.Observable
import rx.schedulers.Schedulers.immediate

class AircraftDetailPresenterTest {
    private val mockedView = Mockito.mock(AircraftDetailView::class.java)

    @Test
    fun showsAircraft() {
        val presenter = AircraftDetailPresenter(
            mainScheduler = immediate(),
            aircraftDetailUseCase = FakeAircraftDetailUseCase(
                withAircraftForId = ID to AIRCRAFT
            ),
            isYoutubeAvailable = { false }
        )

        presenter.startPresenting(mockedView, ID)
        verify(mockedView).showAircraft(AircraftDetailViewModel(AIRCRAFT, null))
    }

    @Test
    fun showsVideoWhenYoutubeAvailableAndAircraftHasVideos() {
        val presenter = AircraftDetailPresenter(
            mainScheduler = immediate(),
            aircraftDetailUseCase = FakeAircraftDetailUseCase(
                withAircraftForId = ID to AIRCRAFT_WITH_VIDEO
            ),
            isYoutubeAvailable = { true }
        )

        presenter.startPresenting(mockedView, ID)
        verify(mockedView).showAircraft(AircraftDetailViewModel(AIRCRAFT_WITH_VIDEO, VIDEO_ID))
    }

    @Test
    fun doesNotShowYoutubeWhenAvailableAndAircraftHasNoVideos() {
        val presenter = AircraftDetailPresenter(
            mainScheduler = immediate(),
            aircraftDetailUseCase = FakeAircraftDetailUseCase(
                withAircraftForId = ID to AIRCRAFT
            ),
            isYoutubeAvailable = { true }
        )

        presenter.startPresenting(mockedView, ID)
        verify(mockedView).showAircraft(AircraftDetailViewModel(AIRCRAFT, null))
    }

    @Test
    fun doesNotShowYoutubeWhenNotAvailableAndAircraftHasVideos() {
        val presenter = AircraftDetailPresenter(
            mainScheduler = immediate(),
            aircraftDetailUseCase = FakeAircraftDetailUseCase(
                withAircraftForId = ID to AIRCRAFT_WITH_VIDEO
            ),
            isYoutubeAvailable = { false }
        )

        presenter.startPresenting(mockedView, ID)
        verify(mockedView).showAircraft(AircraftDetailViewModel(AIRCRAFT_WITH_VIDEO, null))
    }

    @Test
    fun doesNotShowYoutubeWhenNotAvailableAndAircraftHasNoVideos() {
        val presenter = AircraftDetailPresenter(
            mainScheduler = immediate(),
            aircraftDetailUseCase = FakeAircraftDetailUseCase(withAircraftForId = ID to AIRCRAFT),
            isYoutubeAvailable = { false }
        )

        presenter.startPresenting(mockedView, ID)
        verify(mockedView).showAircraft(AircraftDetailViewModel(AIRCRAFT, null))
    }

    private companion object {
        private const val ID = "id"
        private const val VIDEO_ID = "videoId"
        private val AIRCRAFT = Aircraft()
        private val AIRCRAFT_WITH_VIDEO = Aircraft(youtubeVideos = listOf(YoutubeVideo(VIDEO_ID)))
    }
}