package com.edwardharker.aircraftrecognition.perf.firebase

import com.edwardharker.aircraftrecognition.perf.Tracer
import com.edwardharker.aircraftrecognition.perf.firebase.FirebasePerformanceInjector.firebasePerformance
import com.google.firebase.perf.metrics.Trace

class FirebaseTracer private constructor(private val trace: Trace) : Tracer {
    override fun start() {
        trace.start()
    }

    override fun stop() {
        trace.stop()
    }

    companion object {
        fun create(traceName: String): FirebaseTracer {
            return FirebaseTracer(firebasePerformance().newTrace(traceName))
        }
    }
}
