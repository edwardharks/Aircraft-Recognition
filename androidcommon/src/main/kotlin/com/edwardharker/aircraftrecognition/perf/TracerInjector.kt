package com.edwardharker.aircraftrecognition.perf

import com.edwardharker.aircraftrecognition.perf.firebase.FirebaseTracer

object TracerInjector {
    fun tracer(traceName: String): Tracer {
        return LoggingTracer(
            traceName = traceName,
            tracer = FirebaseTracer.create(traceName)
        )
    }
}
