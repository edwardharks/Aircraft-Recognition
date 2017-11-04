package com.edwardharker.aircraftrecognition

import android.app.Application
import com.edwardharker.aircraftrecognition.stetho.initialiseStetho
import com.edwardharker.aircraftrecognition.updater.aircraftUpdater

class AircraftRecognitionApp : Application() {

    override fun onCreate() {
        super.onCreate()
        app = this

        initialiseStetho(this)

        aircraftUpdater().update()
    }

    companion object {
        lateinit var app: AircraftRecognitionApp
    }
}
