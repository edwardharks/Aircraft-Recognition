package com.edwardharker.aircraftrecognition

import android.content.Context

fun aircraftRecognitionApp(): AircraftRecognitionApp = AircraftRecognitionApp.app

fun applicationContext(): Context = aircraftRecognitionApp()
