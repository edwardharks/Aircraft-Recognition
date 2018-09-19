package com.edwardharker.aircraftrecognition.aircraftdetail

import rx.Scheduler
import rx.subscriptions.CompositeSubscription

class AircraftDetailPresenter(
    private val mainScheduler: Scheduler,
    private val aircraftDetailUseCase: AircraftDetailUseCase,
    private val isYoutubeAvailable: () -> Boolean
) {
    private val subscriptions = CompositeSubscription()

    fun startPresenting(view: AircraftDetailView, aircraftId: String) {
        subscriptions.add(aircraftDetailUseCase.getAircraft(aircraftId)
            .subscribeOn(mainScheduler)
            .observeOn(mainScheduler)
            .subscribe { aircraft ->
                val featuredVideoId =
                    if (aircraft.youtubeVideos.isNotEmpty() && isYoutubeAvailable()) {
                        aircraft.youtubeVideos.first().videoId
                    } else null

                view.showAircraft(
                    AircraftDetailViewModel(
                        aircraft = aircraft,
                        featuredVideoId = featuredVideoId
                    )
                )
            })
    }

    fun stopPresenting() {
        subscriptions.unsubscribe()
    }
}
