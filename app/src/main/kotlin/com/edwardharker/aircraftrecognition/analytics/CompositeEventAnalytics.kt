package com.edwardharker.aircraftrecognition.analytics

class CompositeEventAnalytics(vararg private val eventAnalytics: EventAnalytics) : EventAnalytics {

    override fun logEvent(event: Event) {
        eventAnalytics.forEach { it.logEvent(event) }
    }
}
