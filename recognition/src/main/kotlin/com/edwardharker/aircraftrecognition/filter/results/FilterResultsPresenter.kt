package com.edwardharker.aircraftrecognition.filter.results

import com.edwardharker.aircraftrecognition.model.Aircraft
import rx.Observable
import rx.Scheduler
import rx.lang.kotlin.addTo
import rx.subscriptions.CompositeSubscription

class FilterResultsPresenter(
    private val mainScheduler: Scheduler,
    private val filterResultsUseCase: () -> Observable<List<Aircraft>>
) {
    private val subscriptions = CompositeSubscription()

    fun startPresenting(view: FilterResultsView) {
        filterResultsUseCase.invoke()
            .subscribeOn(mainScheduler)
            .observeOn(mainScheduler)
            .subscribe {
                view.showAircraft(it)
            }
            .addTo(subscriptions)
    }


    fun stopPresenting() {
        subscriptions.unsubscribe()
    }
}
