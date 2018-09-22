package com.edwardharker.aircraftrecognition

import android.content.Context

fun aircraftRecognitionApp(): AircraftRecognitionApp {
    return AircraftRecognitionApp.app
}

fun applicationContext(): Context {
    return aircraftRecognitionApp()
}
