package com.edwardharker.aircraftrecognition.analytics.google

import com.edwardharker.aircraftrecognition.analytics.Event
import com.edwardharker.aircraftrecognition.analytics.EventAnalytics
import com.edwardharker.aircraftrecognition.analytics.ScreenView
import com.google.android.gms.analytics.HitBuilders
import com.google.android.gms.analytics.Tracker

class GoogleEventAnalytics(private val tracker: Tracker) : EventAnalytics {

    override fun logEvent(event: Event) {
        tracker.send(event.toGaEvent())
    }

    override fun logScreenView(screenView: ScreenView) {
        tracker.setScreenName(screenView.screenName)
        tracker.send(HitBuilders.ScreenViewBuilder().build())
    }
}
