package com.edwardharker.aircraftrecognition.perf

import com.edwardharker.aircraftrecognition.perf.TracerInjector.tracer

object TracerFactory {
    fun filterActivityContentLoadTracer(): Tracer {
        return tracer("filter_activity_content_load")
    }

    fun filterPickerLoadTracer(): Tracer {
        return tracer("filter_picker_load")
    }

    fun aircraftDetailActivityContentLoad(): Tracer {
        return tracer("aircraft_detail_content_load")
    }
}
