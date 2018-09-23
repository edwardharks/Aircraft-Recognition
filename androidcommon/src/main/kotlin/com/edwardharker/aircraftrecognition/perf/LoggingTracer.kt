package com.edwardharker.aircraftrecognition.perf

import android.util.Log
import com.edwardharker.aircraftrecognition.androidcommon.BuildConfig

class LoggingTracer(
    private val traceName: String,
    private val tracer: Tracer
) : Tracer {
    override fun start() {
        if (BuildConfig.DEBUG) {
            Log.i("Tracer", "Starting trace: $traceName")
        }
        tracer.start()
    }

    override fun stop() {
        if (BuildConfig.DEBUG) {
            Log.i("Tracer", "Stopping trace: $traceName")
        }
        tracer.stop()
    }
}
