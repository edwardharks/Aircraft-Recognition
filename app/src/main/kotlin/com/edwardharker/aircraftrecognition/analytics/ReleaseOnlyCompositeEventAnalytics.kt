package com.edwardharker.aircraftrecognition.analytics

class ReleaseOnlyCompositeEventAnalytics(private val releaseBuild: Boolean,
                                         vararg private val eventAnalytics: EventAnalytics) : EventAnalytics {

    override fun logEvent(event: Event) {
        if (releaseBuild) {
            eventAnalytics.forEach { it.logEvent(event) }
        }
    }

    override fun logScreenView(screenView: ScreenView) {
        if (releaseBuild) {
            eventAnalytics.forEach { it.logScreenView(screenView) }
        }
    }
}
