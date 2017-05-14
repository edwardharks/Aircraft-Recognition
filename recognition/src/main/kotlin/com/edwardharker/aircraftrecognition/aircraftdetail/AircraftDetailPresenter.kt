package com.edwardharker.aircraftrecognition.aircraftdetail

import com.edwardharker.aircraftrecognition.model.Aircraft
import rx.Observable
import rx.Scheduler
import rx.subscriptions.CompositeSubscription

class AircraftDetailPresenter(private val mainScheduler: Scheduler,
                              private val aircraftDetailUseCase: (String) -> Observable<Aircraft>,
                              private val isYoutubeAvailable: () -> Boolean) {

    private val subscriptions = CompositeSubscription()

    fun startPresenting(view: AircraftDetailView, aircraftId: String) =
            subscriptions.add(aircraftDetailUseCase.invoke(aircraftId)
                    .subscribeOn(mainScheduler)
                    .observeOn(mainScheduler)
                    .subscribe {
                        val featuredVideoId = if (it.youtubeVideos.isNotEmpty() && isYoutubeAvailable.invoke()) {
                            it.youtubeVideos.first().videoId
                        } else {
                            null
                        }
                        view.showAircraft(AircraftDetailViewModel(
                                aircraft = it,
                                featuredVideoId = featuredVideoId
                        ))
                    })

    fun stopPresenting() = subscriptions.unsubscribe()
}