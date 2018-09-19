package com.edwardharker.aircraftrecognition.aircraftdetail

import rx.Scheduler
import rx.subscriptions.CompositeSubscription

class PhotoCarouselPresenter(
    private val mainScheduler: Scheduler,
    private val aircraftDetailUseCase: AircraftDetailUseCase
) {
    private val subscriptions = CompositeSubscription()

    fun startPresenting(view: PhotoCarouselView, aircraftId: String?) {
        if (aircraftId == null) {
            view.dismiss()
            return
        }

        subscriptions.add(aircraftDetailUseCase.getAircraft(aircraftId)
            .subscribeOn(mainScheduler)
            .observeOn(mainScheduler)
            .map { it.images }
            .subscribe { view.showImages(it) })
    }

    fun stopPresenting() {
        subscriptions.unsubscribe()
    }
}
