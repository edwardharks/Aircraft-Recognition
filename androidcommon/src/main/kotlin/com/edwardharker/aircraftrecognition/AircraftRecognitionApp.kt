package com.edwardharker.aircraftrecognition

import android.app.Application
import com.edwardharker.aircraftrecognition.repository.aircraftRepository
import com.edwardharker.aircraftrecognition.repository.staticAircraftRepository
import com.edwardharker.aircraftrecognition.stetho.initialiseStetho
import rx.android.schedulers.AndroidSchedulers.mainThread
import rx.schedulers.Schedulers.io

class AircraftRecognitionApp : Application() {

    override fun onCreate() {
        super.onCreate()
        app = this

        initialiseStetho(this)

        // TODO refactor this and unit test
        staticAircraftRepository()
                .allAircraft()
                .subscribeOn(io())
                .observeOn(mainThread())
                .subscribe {
                    aircraftRepository()
                            .deleteAllAircraft()
                    aircraftRepository()
                            .saveAircraft(it)
                }

    }

    companion object {
        lateinit var app: AircraftRecognitionApp
    }
}
