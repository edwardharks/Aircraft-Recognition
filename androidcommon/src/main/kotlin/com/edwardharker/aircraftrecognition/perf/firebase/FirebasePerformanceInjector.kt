package com.edwardharker.aircraftrecognition.perf.firebase

import com.google.firebase.perf.FirebasePerformance

object FirebasePerformanceInjector {
    fun firebasePerformance(): FirebasePerformance {
        return FirebasePerformance.getInstance()
    }
}
