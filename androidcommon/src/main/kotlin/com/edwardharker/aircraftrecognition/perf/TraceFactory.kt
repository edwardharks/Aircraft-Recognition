package com.edwardharker.aircraftrecognition.perf

import com.edwardharker.aircraftrecognition.perf.firebase.FirebaseTracer

object TraceFactory {
    fun filterActivityContentLoadTracer(): Tracer {
        return FirebaseTracer.create("filter_activity_content_load")
    }
}
