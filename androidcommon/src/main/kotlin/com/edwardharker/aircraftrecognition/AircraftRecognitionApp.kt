package com.edwardharker.aircraftrecognition

import android.app.Application
import com.edwardharker.aircraftrecognition.aircraftupdater.aircraftUpdater
import com.edwardharker.aircraftrecognition.android.firstInstall
import com.edwardharker.aircraftrecognition.stetho.initialiseStetho

class AircraftRecognitionApp : Application() {
    override fun onCreate() {
        super.onCreate()
        app = this

        initialiseStetho(this)

        firstInstall().saveVersion()

        aircraftUpdater().update()
    }

    companion object {
        lateinit var app: AircraftRecognitionApp
    }
}
