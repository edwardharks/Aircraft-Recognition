package com.edwardharker.aircraftrecognition.aircraftdetail

import com.edwardharker.aircraftrecognition.model.Aircraft
import rx.Observable
import rx.Scheduler
import rx.subscriptions.CompositeSubscription

class AircraftDetailPresenter(private val mainScheduler: Scheduler,
                              private val aircraftDetailUseCase: (String) -> Observable<Aircraft>) {

    private val subscriptions = CompositeSubscription()

    fun startPresenting(view: AircraftDetailView, aircraftId: String) =
            subscriptions.add(aircraftDetailUseCase.invoke(aircraftId)
                    .subscribeOn(mainScheduler)
                    .observeOn(mainScheduler)
                    .subscribe { view.showAircraft(it) })

    fun stopPresenting() = subscriptions.unsubscribe()
}