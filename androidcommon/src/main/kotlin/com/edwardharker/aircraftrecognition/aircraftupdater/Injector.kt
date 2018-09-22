package com.edwardharker.aircraftrecognition.aircraftupdater

import com.edwardharker.aircraftrecognition.repository.aircraftRepository
import com.edwardharker.aircraftrecognition.repository.remoteAircraftRepository
import com.edwardharker.aircraftrecognition.repository.staticAircraftRepository
import rx.android.schedulers.AndroidSchedulers.mainThread
import rx.schedulers.Schedulers.io

fun aircraftUpdater(): AircraftUpdater =
    AircraftUpdater(
        ioScheduler = io(),
        mainScheduler = mainThread(),
        staticAircraftRepository = staticAircraftRepository(),
        remoteAircraftRepository = remoteAircraftRepository(),
        aircraftRepository = aircraftRepository()
    )
