package com.edwardharker.aircraftrecognition.aircraftdetail

import com.edwardharker.aircraftrecognition.model.Aircraft
import com.edwardharker.aircraftrecognition.model.YoutubeVideo
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify
import rx.Observable
import rx.schedulers.Schedulers.immediate

class AircraftDetailPresenterTest {

    private val id = "id"
    private val videoId = "videoId"
    private val aircraft = Aircraft()
    private val aircraftWithVideo = Aircraft(youtubeVideos = listOf(YoutubeVideo(videoId)))
    private val mockedView = Mockito.mock(AircraftDetailView::class.java)

    @Test
    fun showsAircraft() {
        val presenter = AircraftDetailPresenter(immediate(), {
            if (id == it) {
                Observable.just(aircraft)
            } else {
                Observable.empty()
            }
        }, { false })

        presenter.startPresenting(mockedView, id)
        verify(mockedView).showAircraft(AircraftDetailViewModel(aircraft, null))
    }

    @Test
    fun showsVideoWhenYoutubeAvailableAndAircraftHasVideos() {
        val presenter = AircraftDetailPresenter(immediate(), { Observable.just(aircraftWithVideo) }, { true })

        presenter.startPresenting(mockedView, id)
        verify(mockedView).showAircraft(AircraftDetailViewModel(aircraftWithVideo, videoId))
    }

    @Test
    fun doesNotShowYoutubeWhenAvailableAndAircraftHasNoVideos() {
        val presenter = AircraftDetailPresenter(immediate(), { Observable.just(aircraft) }, { true })

        presenter.startPresenting(mockedView, id)
        verify(mockedView).showAircraft(AircraftDetailViewModel(aircraft, null))
    }

    @Test
    fun doesNotShowYoutubeWhenNotAvailableAndAircraftHasVideos() {
        val presenter = AircraftDetailPresenter(immediate(), { Observable.just(aircraftWithVideo) }, { false })

        presenter.startPresenting(mockedView, id)
        verify(mockedView).showAircraft(AircraftDetailViewModel(aircraftWithVideo, null))
    }

    @Test
    fun doesNotShowYoutubeWhenNotAvailableAndAircraftHasNoVideos() {
        val presenter = AircraftDetailPresenter(immediate(), { Observable.just(aircraft) }, { false })

        presenter.startPresenting(mockedView, id)
        verify(mockedView).showAircraft(AircraftDetailViewModel(aircraft, null))
    }
}