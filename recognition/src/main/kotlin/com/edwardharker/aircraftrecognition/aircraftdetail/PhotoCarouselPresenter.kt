package com.edwardharker.aircraftrecognition.aircraftdetail

import com.edwardharker.aircraftrecognition.model.Aircraft
import rx.Observable
import rx.Scheduler
import rx.subscriptions.CompositeSubscription

class PhotoCarouselPresenter(
        private val mainScheduler: Scheduler,
        private val aircraftDetailUseCase: (String) -> Observable<Aircraft>
) {

    private val subscriptions = CompositeSubscription()

    fun startPresenting(view: PhotoCarouselView, aircraftId: String) =
            subscriptions.add(aircraftDetailUseCase.invoke(aircraftId)
                    .subscribeOn(mainScheduler)
                    .observeOn(mainScheduler)
                    .map { it.images }
                    .subscribe { view.showImages(it) })

    fun stopPresenting() = subscriptions.unsubscribe()
}
