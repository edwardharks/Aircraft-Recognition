package com.edwardharker.aircraftrecognition.similaraircraft

import com.edwardharker.aircraftrecognition.model.Aircraft
import rx.Observable
import rx.Scheduler
import rx.subscriptions.CompositeSubscription

class SimilarAircraftPresenter(
    private val mainScheduler: Scheduler,
    private val similarAircraft: (String) -> Observable<List<Aircraft>>
) {
    private val subscriptions = CompositeSubscription()

    fun startPresenting(view: SimilarAircraftMvpView, aircraftId: String) {
        subscriptions.add(similarAircraft(aircraftId)
            .subscribeOn(mainScheduler)
            .observeOn(mainScheduler)
            .subscribe { aircraft ->
                view.showSimilarAircraft(aircraft)
                if (aircraft.isEmpty()) {
                    view.hideView()
                }
            })
    }

    fun stopPresenting() {
        subscriptions.clear()
    }
}
