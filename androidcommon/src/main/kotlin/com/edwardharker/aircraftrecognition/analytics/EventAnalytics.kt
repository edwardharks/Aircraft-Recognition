package com.edwardharker.aircraftrecognition.analytics

interface EventAnalytics {
    fun logEvent(event: Event) = Unit

    fun logScreenView(screenView: ScreenView) = Unit
}
