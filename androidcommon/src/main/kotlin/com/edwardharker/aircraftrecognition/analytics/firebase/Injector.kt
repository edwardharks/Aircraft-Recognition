package com.edwardharker.aircraftrecognition.analytics.firebase

import com.edwardharker.aircraftrecognition.applicationContext
import com.google.firebase.analytics.FirebaseAnalytics

private fun firebaseAnalytics(): FirebaseAnalytics {
    return FirebaseAnalytics.getInstance(applicationContext())
}

fun firebaseEventAnalytics(): FirebaseEventAnalytics {
    return FirebaseEventAnalytics(firebaseAnalytics())
}
