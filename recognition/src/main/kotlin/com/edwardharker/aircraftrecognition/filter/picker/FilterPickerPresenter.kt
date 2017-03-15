package com.edwardharker.aircraftrecognition.filter.picker

import com.edwardharker.aircraftrecognition.model.Filter
import rx.Observable
import rx.Scheduler
import rx.subscriptions.CompositeSubscription

class FilterPickerPresenter(private val mainScheduler: Scheduler,
                            private val filterPickerUseCase: () -> Observable<List<Filter>>) {

    private val subscriptions = CompositeSubscription()

    fun startPresenting(view: FilterPickerView) {
        subscriptions.add(filterPickerUseCase.invoke()
                .subscribeOn(mainScheduler)
                .observeOn(mainScheduler)
                .subscribe { view.showFilters(it) })
    }

    fun stopPresenting() = subscriptions.unsubscribe()
}
