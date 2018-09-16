package com.edwardharker.aircraftrecognition.analytics.firebase

import com.edwardharker.aircraftrecognition.analytics.Event
import com.edwardharker.aircraftrecognition.analytics.EventAnalytics
import com.google.firebase.analytics.FirebaseAnalytics

class FirebaseEventAnalytics(private val firebaseAnalytics: FirebaseAnalytics) : EventAnalytics {
    override fun logEvent(event: Event) {
        firebaseAnalytics.logEvent(event.eventType.toFirebaseEventType(), event.toBundle())
    }
}
