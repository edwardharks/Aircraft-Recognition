package com.edwardharker.aircraftrecognition.updater

import com.edwardharker.aircraftrecognition.repository.AircraftRepository
import rx.Observable
import rx.Scheduler

class AircraftUpdater(
        private val ioScheduler: Scheduler,
        private val mainScheduler: Scheduler,
        private val staticAircraftRepository: AircraftRepository,
        private val remoteAircraftRepository: AircraftRepository,
        private val aircraftRepository: AircraftRepository
) {

    fun update() {
        val staticRepositoryAircraftObservable =
                aircraftRepository.allAircraft()
                        .filter { it.isEmpty() }
                        .flatMap {
                            staticAircraftRepository
                                    .allAircraft()
                                    .subscribeOn(ioScheduler)
                        }

        Observable.merge(
                staticRepositoryAircraftObservable,
                remoteAircraftRepository.allAircraft())
                .subscribeOn(mainScheduler)
                .observeOn(mainScheduler)
                .subscribe { aircraft ->
                    aircraftRepository.deleteAllAircraft()
                    aircraftRepository.saveAircraft(aircraft)
                }
    }
}
