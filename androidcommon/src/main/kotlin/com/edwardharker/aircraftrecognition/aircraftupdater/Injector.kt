package com.edwardharker.aircraftrecognition.aircraftupdater

import com.edwardharker.aircraftrecognition.repository.AircraftRepository
import com.edwardharker.aircraftrecognition.repository.aircraftRepository
import com.edwardharker.aircraftrecognition.repository.staticAircraftRepository
import rx.android.schedulers.AndroidSchedulers.*
import rx.schedulers.Schedulers.*

fun aircraftUpdater(): AircraftUpdater = AircraftUpdater(
        ioScheduler = io(),
        mainScheduler = mainThread(),
        staticAircraftRepository = staticAircraftRepository(),
        remoteAircraftRepository = AircraftRepository.Empty,
        aircraftRepository = aircraftRepository()
)
