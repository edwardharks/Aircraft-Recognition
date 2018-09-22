package com.edwardharker.aircraftrecognition.filter.results

import com.edwardharker.aircraftrecognition.model.Aircraft
import rx.Observable
import rx.Scheduler
import rx.subscriptions.CompositeSubscription

class FilterResultsPresenter(
    private val mainScheduler: Scheduler,
    private val filterResultsUseCase: () -> Observable<List<Aircraft>>
) {
    private val subscriptions = CompositeSubscription()

    fun startPresenting(view: FilterResultsView) {
        subscriptions.add(filterResultsUseCase.invoke()
            .subscribeOn(mainScheduler)
            .observeOn(mainScheduler)
            .subscribe
            { view.showAircraft(it) })
    }


    fun stopPresenting() {
        subscriptions.unsubscribe()
    }
}
