package com.edwardharker.aircraftrecognition.aircraftupdater

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
                aircraftRepository.allAircraftCount()
                        .filter { it == 0L }
                        .flatMap {
                            staticAircraftRepository
                                    .allAircraft()
                                    .subscribeOn(ioScheduler)
                        }

        val remoteRepositoryAircraftObservable =
                remoteAircraftRepository.allAircraft()
                        .subscribeOn(ioScheduler)

        Observable.merge(
                staticRepositoryAircraftObservable,
                remoteRepositoryAircraftObservable)
                .subscribeOn(mainScheduler)
                .observeOn(mainScheduler)
                .subscribe { aircraft ->
                    aircraftRepository.deleteAllAircraft()
                    aircraftRepository.saveAircraft(aircraft)
                }
    }
}
