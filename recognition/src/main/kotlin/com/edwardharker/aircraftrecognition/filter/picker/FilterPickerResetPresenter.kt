package com.edwardharker.aircraftrecognition.filter.picker

import com.edwardharker.aircraftrecognition.filter.SelectedFilterOptions
import rx.Observable
import rx.Scheduler
import rx.subscriptions.CompositeSubscription

class FilterPickerResetPresenter(
    private val mainScheduler: Scheduler,
    private val shouldShowResetUseCase: () -> Observable<Boolean>,
    private val selectedFilterOptions: SelectedFilterOptions
) {
    private val subscriptions = CompositeSubscription()

    fun startPresenting(view: FilterPickerResetView) {
        subscriptions.add(shouldShowResetUseCase.invoke()
            .subscribeOn(mainScheduler)
            .observeOn(mainScheduler)
            .subscribe { shouldShowReset ->
                if (shouldShowReset) {
                    view.showReset()
                } else {
                    view.hideReset()
                }
            })
    }

    fun resetFilters() {
        selectedFilterOptions.deselectAll()
    }

    fun stopPresenting() {
        subscriptions.clear()
    }
}
