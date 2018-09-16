package com.edwardharker.aircraftrecognition.analytics

import com.edwardharker.aircraftrecognition.analytics.firebase.firebaseEventAnalytics
import com.edwardharker.aircraftrecognition.analytics.google.googleEventAnalytics
import com.edwardharker.aircraftrecognition.androidcommon.BuildConfig.*

fun eventAnalytics(): EventAnalytics {
    return ReleaseOnlyCompositeEventAnalytics(
        !DEBUG,
        firebaseEventAnalytics(),
        googleEventAnalytics()
    )
}
